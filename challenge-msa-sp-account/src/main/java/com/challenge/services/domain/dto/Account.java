package com.challenge.services.domain.dto;

import java.math.BigDecimal;

@lombok.Setter
@lombok.Getter
public class Account {

    private String accountId;
    private String accountNumber;
    private BigDecimal initialBalance;

    private Type type;
    private Status status;
    private BigDecimal customerId;
}
