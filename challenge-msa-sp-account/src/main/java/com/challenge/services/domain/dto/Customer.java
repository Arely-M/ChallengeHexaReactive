package com.challenge.services.domain.dto;

@lombok.Setter
@lombok.Getter
public class Customer {

    private String customerId;
    private String password;
    private Status status;

    private String name;
    private Gender gender;
    private int age;
    private Identification identification;
    private Address address;
    private PhoneAddress phoneAddress;
}
