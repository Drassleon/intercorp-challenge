package org.intercorpretail.challenge.retail.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderProducts {

    @Id
    private String id;

    @Column("order_id")
    private String orderId;

    @Column("product_sku")
    private String productSku;

    @Column("product_name")
    private String productName;

    @Column("quantity")
    private Double quantity;

    @Column("price")
    private Double price;

    @Column("discount")
    private Double discount;

}
