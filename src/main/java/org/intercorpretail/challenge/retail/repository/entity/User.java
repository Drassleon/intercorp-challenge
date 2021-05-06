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
public class User {

    @Id
    private String id;

    @Column("name")
    private String name;

    @Column("last_name")
    private String lastName;

    @Column("document_number")
    private String documentNumber;

    @Column("document_type")
    private Integer documentType;

    @Column("phone_number")
    private String phoneNumber;

    @Column("created_date")
    private LocalDateTime createdDate;

    @Column("last_modified")
    private LocalDateTime lastModified;
}
