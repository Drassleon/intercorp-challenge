package org.intercorpretail.challenge.retail.repository;

import org.intercorpretail.challenge.retail.repository.entity.Order;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface OrderRepository extends ReactiveCrudRepository<Order, String> {
    @Modifying
    @Query(value = "insert into `order`(id,user_id,total,sub_total,taxes,discounts,created_date) VALUES(:id,:userId,:total,:subTotal,:taxes,:discounts,:createdDate)")
    Flux<Integer> insertOrder(@Param("id") String id,
                              @Param("userId") String userId,
                              @Param("total") Double total,
                              @Param("subTotal") Double subTotal,
                              @Param("taxes") Double taxes,
                              @Param("discounts") Double discounts,
                              @Param("createdDate") LocalDateTime createdDate
    );
}
