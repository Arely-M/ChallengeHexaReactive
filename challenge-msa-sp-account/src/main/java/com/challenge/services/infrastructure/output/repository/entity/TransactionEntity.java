package com.challenge.services.infrastructure.output.repository.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "Transactions")
@AllArgsConstructor
@NoArgsConstructor
@lombok.Getter
@lombok.Setter
public class TransactionEntity {

    @Id
    @Column("transactionid")
    private Integer id; // PK
    private String date;
    @Column("transactiontype")
    private String transactionType;
    private double value;
    private double balance;
    @Column("initialbalance")
    private double initialBalance;
    @Column("accountid")
    private int accountId;
}
