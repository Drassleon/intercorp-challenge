package org.intercorpretail.challenge.retail.controller;

import org.intercorpretail.challenge.retail.business.domain.Product;
import org.intercorpretail.challenge.retail.business.output.OrderProductsService;
import org.intercorpretail.challenge.retail.business.output.OrderService;
import org.intercorpretail.challenge.retail.business.output.ProductService;
import org.intercorpretail.challenge.retail.business.output.UserService;
import org.intercorpretail.challenge.retail.controller.request.CreateOrderRequest;
import org.intercorpretail.challenge.retail.controller.request.CreateProductRequest;
import org.intercorpretail.challenge.retail.repository.entity.Order;
import org.intercorpretail.challenge.retail.repository.entity.User;
import org.intercorpretail.challenge.utils.exception.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderService orderService;

    @Mock
    OrderProductsService orderProductsService;

    @Mock
    UserService userService;

    @Mock
    ProductService productService;

    static CreateOrderRequest createRequest;

    static String productSku;

    static User user;

    static String userId;

    static Product product;

    @BeforeAll
    static void init() {
        productSku = "productSku";
        userId = UUID.randomUUID().toString();
        createRequest = CreateOrderRequest.builder()
                .userId(userId)
                .productListRequest(Collections.singletonList(CreateProductRequest.builder().quantity(1.0).sku(productSku).build()))
                .build();

        user = User.builder()
                .id(userId)
                .phoneNumber("992252496")
                .lastModified(LocalDateTime.now())
                .lastName("lastName")
                .name("testSubject")
                .documentType(2)
                .documentNumber("000123456")
                .createdDate(LocalDateTime.now())
                .build();

        product = Product.builder()
                .skuId(productSku)
                .discount("1.0")
                .id(123)
                .name("testProduct")
                .price("1.0")
                .build();
    }

    @Test
    void testCreateOrder_GivenValidInputs_ShouldReturnNothing() throws EntityNotFoundException {

        Mockito.when(this.userService.findUserById(userId)).thenReturn(Mono.just(user));

        Mockito.when(this.productService.getBySkuId(productSku)).thenReturn(product);

        Mockito.doNothing().when(this.orderProductsService).saveProductsForOrder(any(List.class), anyString());

        Mockito.doNothing().when(this.orderService).save(any(Order.class));

        this.orderController.createOrder(createRequest);

        verify(this.userService, times(1)).findUserById(userId);
        verify(this.productService, times(1)).getBySkuId(productSku);
        verify(this.orderProductsService, times(1)).saveProductsForOrder(any(List.class), anyString());
        verify(this.orderService, times(1)).save(any(Order.class));
    }

    @Test
    void testCreateOrder_GivenValidInputsButNoItemsWereFound_ShouldThrowEntityNotFoundException() {

        Mockito.when(this.userService.findUserById(userId)).thenReturn(Mono.just(user));

        Mockito.when(this.productService.getBySkuId(productSku)).thenReturn(null);

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> this.orderController.createOrder(createRequest));

        verify(this.userService, times(1)).findUserById(userId);
        verify(this.productService, times(1)).getBySkuId(productSku);
        Assertions.assertNotNull(exception);
    }
}