package com.challenge.services.infrastructure.output.repository.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@lombok.Getter
@lombok.Setter
public class Person {

    private String name;
    private String gender;
    private int age;
    private String identification;
    private String address;
    private String phone;
}
