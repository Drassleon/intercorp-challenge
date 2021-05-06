package org.intercorpretail.challenge.retail.business;

import lombok.extern.slf4j.Slf4j;
import org.intercorpretail.challenge.retail.business.domain.Product;
import org.intercorpretail.challenge.retail.business.output.OrderProductsService;
import org.intercorpretail.challenge.retail.repository.OrderProductsRepository;
import org.intercorpretail.challenge.retail.repository.entity.OrderProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderProductsServiceImpl implements OrderProductsService {

    private final OrderProductsRepository orderProductsRepository;

    @Autowired
    public OrderProductsServiceImpl(OrderProductsRepository orderProductsRepository) {
        this.orderProductsRepository = orderProductsRepository;
    }

    @Override
    public void saveProductsForOrder(List<Product> products, String orderId) {
        products.stream()
                .map(product -> buildOrderProductEntity(product, orderId))
                .forEach(orderProducts -> this.orderProductsRepository.insertOrderProduct(
                        orderProducts.getId(),
                        orderProducts.getOrderId(),
                        orderProducts.getProductSku(),
                        orderProducts.getProductName(),
                        orderProducts.getQuantity(),
                        orderProducts.getPrice(),
                        orderProducts.getDiscount())
                        .subscribe(integer -> log.info("Saved item for order {} with the following result: {}", orderId, integer > 0 ? "SUCCESS" : "FAILED")));
    }

    private OrderProducts buildOrderProductEntity(Product product, String orderId) {
        return OrderProducts.builder()
                .id(UUID.randomUUID().toString())
                .productName(product.getName())
                .orderId(orderId)
                .productSku(product.getSkuId())
                .price(Double.valueOf(product.getPrice()))
                .quantity(product.getAmount())
                .discount(Double.valueOf(product.getDiscount()))
                .build();
    }
}
