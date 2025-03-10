package com.challenge.services.util;

import com.challenge.services.input.server.models.*;

import java.math.BigDecimal;

public class MockObjects {
    private MockObjects(){}

    public static PostCustomerRequest buildPostCustomerRequest() {
        return new PostCustomerRequest()
                .customerId("1")
                .age(BigDecimal.valueOf(30))
                .address(new Address()
                        .value("Urdesa"))
                .name("Arely")
                .gender(new Gender()
                        .code("F"))
                .identification(new Identification()
                        .type(new TypeOfIdentification()
                                .code("IDCD")
                                .name("Cedula"))
                        .identifier(new Identifier()
                                .value("1235361007")))
                .password("123456")
                .status(new Status()
                        .code("Enable"))
                .phoneAddress(new PhoneAddress()
                        .description("09012343"));
    }

}
