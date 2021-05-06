package org.intercorpretail.challenge.retail.controller;

import lombok.extern.slf4j.Slf4j;
import org.intercorpretail.challenge.retail.business.domain.Product;
import org.intercorpretail.challenge.retail.business.output.OrderProductsService;
import org.intercorpretail.challenge.retail.business.output.OrderService;
import org.intercorpretail.challenge.retail.business.output.ProductService;
import org.intercorpretail.challenge.retail.business.output.UserService;
import org.intercorpretail.challenge.retail.controller.request.CreateOrderRequest;
import org.intercorpretail.challenge.retail.repository.entity.Order;
import org.intercorpretail.challenge.retail.repository.entity.User;
import org.intercorpretail.challenge.utils.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/orders")
@Slf4j
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
public class OrderController {

    private final OrderService orderService;

    private final OrderProductsService orderProductsService;

    private final UserService userService;

    private final ProductService productService;

    @Autowired
    public OrderController(OrderService orderService,
                           OrderProductsService orderProductsService,
                           UserService userService,
                           ProductService productService) {
        this.orderService = orderService;
        this.orderProductsService = orderProductsService;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@Valid @RequestBody CreateOrderRequest request) throws EntityNotFoundException {
        log.info("Getting user data for id: {}", request.getUserId());
        var user = Optional.ofNullable(this.userService.findUserById(request.getUserId()).block()).orElseThrow(() -> new EntityNotFoundException(User.class, request.getUserId()));

        var orderId = UUID.randomUUID().toString();

        log.info("Searching for requested products");
        List<Product> products = request.getProductListRequest()
                .stream()
                .map(createProductRequest -> {
                    var product = this.productService.getBySkuId(createProductRequest.getSku());
                    if (product == null) {
                        return null;
                    }
                    product.setAmount(createProductRequest.getQuantity());
                    return product;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (products.isEmpty() || products.size() < request.getProductListRequest().size()) {
            log.error("Could not find some or all products for these SKUs {}", request.getProductListRequest().toString());
            throw new EntityNotFoundException(Product.class, request.getProductListRequest().toString());
        }
        log.info("Saving order items");
        this.orderProductsService.saveProductsForOrder(
                products,
                orderId);
        log.info("Calculating total, subtotal and discounts");
        Mono<Double> asyncSubTotal = Mono.just(products.stream().map(product -> Double.parseDouble(product.getPrice()) * product.getAmount()).reduce(Double::sum).orElse(0.0));
        Mono<Double> asyncDiscounts = Mono.just(products.stream().map(product -> Double.parseDouble(product.getDiscount()) * product.getAmount()).reduce(Double::sum).orElse(0.0));
        Mono<Double> asyncTaxes = asyncSubTotal.zipWith(asyncDiscounts).map(tuple -> tuple.getT1() - tuple.getT2() > 100 ?
                calculateTaxes(tuple.getT1(), tuple.getT2()) : 0);

        asyncSubTotal.subscribe(subtotal -> log.info("Finished calculating subtotal with value of {}", subtotal));
        asyncDiscounts.subscribe(discounts -> log.info("Finished calculating discounts with value of {}", discounts));
        asyncTaxes.subscribe(taxes -> log.info("Finished calculating taxes with value of {}", taxes));

        log.info("Persisting order data to database");
        this.orderService.save(Order.builder()
                .id(orderId)
                .createdDate(LocalDateTime.now())
                .discounts(Optional.ofNullable(asyncDiscounts.block()).orElse(0.0))
                .taxes(asyncTaxes.block())
                .subTotal(asyncSubTotal.block())
                .total(calculateTotal(Optional.ofNullable(asyncSubTotal.block()).orElse(0.0)
                        , Optional.ofNullable(asyncTaxes.block()).orElse(0.0)
                        , Optional.ofNullable(asyncDiscounts.block()).orElse(0.0)))
                .userId(user.getId())
                .build());
    }

    private Double formatDouble(Double number) {
        BigDecimal tempNumber = BigDecimal.valueOf(number).setScale(2, RoundingMode.HALF_DOWN);
        return tempNumber.doubleValue();
    }

    private Double calculateTaxes(Double subTotal, Double discount) {
        return formatDouble((subTotal - discount) * 0.10);
    }

    private Double calculateTotal(Double subTotal, Double discount, Double taxes) {
        var payment = Double.sum(subTotal, taxes);
        return Double.sum(payment, discount * -1);
    }
}
