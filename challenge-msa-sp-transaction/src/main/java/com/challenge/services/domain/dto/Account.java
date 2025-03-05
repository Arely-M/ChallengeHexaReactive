package com.challenge.services.domain.dto;

import java.math.BigDecimal;

@lombok.Setter
@lombok.Getter
public class Account {

    private String accountId;
    private String accountNumber;
    private Type type;
    private BigDecimal initialBalance;
    private Status status;
    private String customerId;
}
