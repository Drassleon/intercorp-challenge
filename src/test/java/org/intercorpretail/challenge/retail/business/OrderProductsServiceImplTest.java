package org.intercorpretail.challenge.retail.business;

import org.intercorpretail.challenge.retail.business.domain.Product;
import org.intercorpretail.challenge.retail.repository.OrderProductsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderProductsServiceImplTest {

    @InjectMocks
    OrderProductsServiceImpl orderProductsService;

    @Mock
    OrderProductsRepository orderProductsRepository;

    @Test
    void testSaveProductsForOrder_GivenValidParameters_ShouldReturnNothing() {
        Product product = Product.builder()
                .id(123)
                .price("1.0")
                .name("testProduct")
                .amount(1.0)
                .discount("1.0")
                .skuId("skuId")
                .build();
        List<Product> products = Collections.singletonList(product);

        String orderId = UUID.randomUUID().toString();

        Flux<Integer> result = Flux.just(1);

        Mockito.when(this.orderProductsRepository.insertOrderProduct(anyString(), anyString(), anyString(), anyString(), anyDouble(), anyDouble(), anyDouble())).thenReturn(result);
        this.orderProductsService.saveProductsForOrder(products, orderId);
        verify(this.orderProductsRepository, times(1)).insertOrderProduct(anyString(), anyString(), anyString(), anyString(), anyDouble(), anyDouble(), anyDouble());
    }
}