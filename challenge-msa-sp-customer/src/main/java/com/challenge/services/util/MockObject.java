package com.challenge.services.util;

import java.math.BigDecimal;

public class MockObject {

    private MockObject(){}

    public static com.challenge.services.domain.dto.Customer postCustomerDto(){
        return getCustomer();
    }

    public static com.challenge.services.domain.dto.Customer getCustomer() {
        com.challenge.services.domain.dto.Customer customer = new com.challenge.services.domain.dto.Customer();

        customer.setCustomerId("1");
        customer.setAge(BigDecimal.valueOf(30));

        com.challenge.services.domain.dto.Address address = new com.challenge.services.domain.dto.Address();
        customer.setAddress(address);

        customer.setName("Arely");

        com.challenge.services.domain.dto.Gender gender = new com.challenge.services.domain.dto.Gender();
        gender.setCode("F");
        customer.setGender(gender);

        com.challenge.services.domain.dto.Identifier identifier = new com.challenge.services.domain.dto.Identifier();
        identifier.setValue("1235361007");
        com.challenge.services.domain.dto.Identification identification = new com.challenge.services.domain.dto.Identification();
        identification.setIdentifier(identifier);
        customer.setIdentification(identification);

        customer.setPassword("12345fsd");

        com.challenge.services.domain.dto.Status status = new com.challenge.services.domain.dto.Status();
        status.setCode("Enable");
        customer.setStatus(status);

        com.challenge.services.domain.dto.PhoneAddress phoneAddress = new com.challenge.services.domain.dto.PhoneAddress();
        phoneAddress.setDescription("09012343");
        customer.setPhoneAddress(phoneAddress);

        return customer;
    }

}
