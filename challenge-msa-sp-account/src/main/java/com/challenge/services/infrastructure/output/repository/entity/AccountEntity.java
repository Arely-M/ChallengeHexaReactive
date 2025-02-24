package com.challenge.services.infrastructure.output.repository.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountEntity {

    @Id
    @Column("accountid")
    private Integer id; // PK
    @Column("accountnumber")
    private String accountNumber;
    @Column("accounttype")
    private String accountType;
    @Column("initialbalance")
    private double initialBalance;
    private String status;
    @Column("customerid")
    private int customerId;

}
