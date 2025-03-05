package com.challenge.services.domain.dto;

import java.math.BigDecimal;

@lombok.Setter
@lombok.Getter
public class Customer {

    private String customerId;
    private String name;
    private Gender gender;
    private BigDecimal age;
    private Identification identification;
    private Address address;
    private PhoneAddress phoneAddress;
    private String password;
    private Status status;


}
