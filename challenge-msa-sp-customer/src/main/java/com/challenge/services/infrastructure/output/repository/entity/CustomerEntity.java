package com.challenge.services.infrastructure.output.repository.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerEntity extends PersonEntity {

    @Id
    @Column("customerid")
    private Integer id; // PK
    private String password;
    private String status;


}
