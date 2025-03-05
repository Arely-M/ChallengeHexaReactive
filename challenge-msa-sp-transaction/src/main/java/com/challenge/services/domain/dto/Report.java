package com.challenge.services.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

@lombok.Setter
@lombok.Getter
public class Report {

    private LocalDate date;
    private String customerName;
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private Status status;
    private BigDecimal value;
    private BigDecimal availableBalance;
}
