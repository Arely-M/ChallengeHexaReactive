package com.challenge.services.infrastructure.input.adapter.rest.mapper;

import com.challenge.services.domain.dto.Customer;
import com.challenge.services.domain.dto.Subject;
import com.challenge.services.input.server.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer mapperPostCustomerRequestToCustomerDto(PostCustomerRequest postCustomerRequest);

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
    Customer mapperPatchCustomerRequestToCustomerDto(PutCustomerRequest putCustomerRequest);

    @Mapping(target = "value", source = "subject")
    Subject mapperPostCustomerGeneratedTokenRequestToString(PostCustomerGeneratedTokenRequest postCustomerGeneratedTokenRequest);

    @Mapping(target = "jwt", source = "jwt")
    PostCustomerGeneratedTokenResponse mapperJwtToPostCustomerGeneratedTokenResponse(String jwt);


}
