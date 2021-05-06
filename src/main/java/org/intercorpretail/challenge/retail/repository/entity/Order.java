package org.intercorpretail.challenge.retail.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;


import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    @Id
    private String id;

    @Column("user_id")
    private String userId;

    @Column("total")
    private Double total;

    @Column("sub_total")
    private Double subTotal;

    @Column("taxes")
    private Double taxes;

    @Column("discounts")
    private double discounts;

    @Column("created_date")
    private LocalDateTime createdDate;
}
