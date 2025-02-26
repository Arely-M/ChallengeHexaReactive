package com.challenge.services.util;

import com.challenge.services.input.server.models.*;
import java.math.BigDecimal;

public class MockObject {

    private MockObject(){}

    public static PostCustomerRequest buildPostCustomerRequest() {
        return new PostCustomerRequest()
                .customerId("1")
                .age(BigDecimal.valueOf(30))
                .address(new Address())
                .name("Arely")
                .gender(new Gender().code(Gender.CodeEnum.F))
                .identification(new Identification()
                        .identifier(new Identifier()
                                .value("1235361007")))
                .password("12345")
                .status(new Status()
                        .code("Enable"))
                .phoneAddress(new PhoneAddress()
                        .description("09012343"));
    }

    public static Customer buildGetCustomer() {
        return new Customer()
                .customerId("1")
                .age(BigDecimal.valueOf(30))
                .address(new Address())
                .name("Arely")
                .gender(new Gender().code(Gender.CodeEnum.F))
                .identification(new Identification()
                        .identifier(new Identifier()
                                .value("1235361007")))
                .password("12345")
                .status(new Status()
                        .code("Enable"))
                .phoneAddress(new PhoneAddress()
                        .description("09012343"));
    }

}
