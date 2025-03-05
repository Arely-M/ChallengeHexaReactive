package com.challenge.services.infrastructure.input.adapter.rest.mapper;

import com.challenge.services.domain.dto.Customer;
import com.challenge.services.input.server.models.PatchCustomerRequest;
import com.challenge.services.input.server.models.PostCustomerRequest;
import com.challenge.services.input.server.models.PutCustomerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "gender", ignore = true)
    Customer mapperPostCustomerRequestToCustomerDto(PostCustomerRequest postCustomerRequest);

    @Mapping(target = "gender", ignore = true)
    com.challenge.services.input.server.models.Customer mapperCustomerDtoToCustomerResponse(Customer customer);

    @Mapping(target = "phoneAddress", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "identification", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "address", ignore = true)
    Customer mapperPatchCustomerRequestToCustomerDto(PatchCustomerRequest patchCustomerRequest);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "gender", ignore = true)
    Customer mapperPatchCustomerRequestToCustomerDto(PutCustomerRequest putCustomerRequest);

}
