package org.intercorpretail.challenge.retail.repository;

import org.intercorpretail.challenge.retail.repository.entity.OrderProducts;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface OrderProductsRepository extends ReactiveCrudRepository<OrderProducts, String> {

    @Modifying
    @Query(value = "insert into order_products(id,order_id,product_sku,product_name,quantity,price,discount) VALUES(:id,:order_id,:product_sku,:product_name,:quantity,:price,:discount)")
    Flux<Integer> insertOrderProduct(@Param("id") String id,
                            @Param("order_id") String orderId,
                            @Param("product_sku") String productSku,
                            @Param("product_name") String productName,
                            @Param("quantity") Double quantity,
                            @Param("price") Double price,
                            @Param("discount") Double discount
    );

    @Query("select * from order_products where order_id = :orderId")
    Flux<OrderProducts> getOrderProductsUsingOrderId(@Param("orderId") String orderId);
}
