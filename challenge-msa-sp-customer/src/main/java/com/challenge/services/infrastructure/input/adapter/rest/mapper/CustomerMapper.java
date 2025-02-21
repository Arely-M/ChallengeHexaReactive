package com.challenge.services.infrastructure.input.adapter.rest.mapper;

import com.challenge.services.input.server.models.Customer;
import com.challenge.services.input.server.models.PostCustomerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer mapperToCustomer(PostCustomerRequest postCustomerRequest);
}
