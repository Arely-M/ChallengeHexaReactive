package com.challenge.services.infrastructure.output.repository.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.relational.core.mapping.Column;

@AllArgsConstructor
@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepostEntity {

    private String date;
    @Column("name")
    private String customerName;
    @Column("accountnumber")
    private String accountNumber;
    @Column("accounttype")
    private String accountType;
    @Column("initialbalance")
    private double initialBalance;
    private String status;
    private double value;
    @Column("balance")
    private double availableBalance;
}
