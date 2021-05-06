package org.intercorpretail.challenge.retail.business;

import org.intercorpretail.challenge.retail.repository.OrderRepository;
import org.intercorpretail.challenge.retail.repository.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    @Test
    void testSave_GivenValidInputs_ShouldReturnNothing() {

        Order order = Order.builder()
                .userId("userId")
                .total(2.0)
                .subTotal(2.0)
                .id("id")
                .taxes(2.0)
                .discounts(2.0)
                .createdDate(LocalDateTime.now())
                .build();

        Flux<Integer> databaseResponse = Flux.just(1);

        Mockito.when(this.orderRepository.insertOrder(anyString(), anyString(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), any(LocalDateTime.class))).thenReturn(databaseResponse);

        this.orderService.save(order);
        Mockito.verify(this.orderRepository, Mockito.times(1)).insertOrder(anyString(), anyString(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), any(LocalDateTime.class));
    }
}