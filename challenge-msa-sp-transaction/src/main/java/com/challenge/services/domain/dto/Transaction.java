package com.challenge.services.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

@lombok.Setter
@lombok.Getter
public class Transaction {

    private String transactionId;
    private LocalDate date;
    private Type type;
    private BigDecimal value;
    private BigDecimal balance;
    private BigDecimal accountId;
}
