package com.challenge.services.domain.dto;

@lombok.Setter
@lombok.Getter
public class Customer {

    private Person person;
    private int id;
    private String password;
    private String status;
}
