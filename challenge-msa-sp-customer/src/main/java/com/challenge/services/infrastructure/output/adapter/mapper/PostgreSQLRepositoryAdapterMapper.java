package com.challenge.services.infrastructure.output.adapter.mapper;

import com.challenge.services.infrastructure.output.repository.entity.Customer;
import com.challenge.services.input.server.models.*;
import com.challenge.services.util.Constants;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostgreSQLRepositoryAdapterMapper {

    PostgreSQLRepositoryAdapterMapper INSTANCE = Mappers.getMapper(PostgreSQLRepositoryAdapterMapper.class);

//    @Mapping(target = "customerId", source = "id")
//    @Mapping(target = "phoneAddress.description", source = "phone")
//    @Mapping(target = "gender", ignore = true)
//    @Mapping(target = "identification.identifier.value", source = "identification")
//    @Mapping(target = "address.value", source = "address")
//    @Mapping(target = "status.code", source = "status")
//    com.challenge.services.input.server.models.Customer mapperToCustomer(com.challenge.services.infrastructure.output.repository.entity.Customer customer);

    @Mapping(target = "customerId", source = "id")
    @Mapping(target = "phoneAddress.description", source = "phone")
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "identification.identifier.value", source = "identification")
    @Mapping(target = "address.value", source = "address")
    @Mapping(target = "status.code", source = "status")
    com.challenge.services.domain.dto.Customer mapperCustomerEntityToCustomerDto(com.challenge.services.infrastructure.output.repository.entity.Customer customer);



//    @AfterMapping
//    default void afterCustomer(@MappingTarget com.challenge.services.domain.dto.Customer customer, com.challenge.services.infrastructure.output.repository.entity.Customer customerEntity) {
//        Gender gender = new Gender();
//        Identification identification = new Identification();
//        TypeOfIdentification typeOfIdentification = new TypeOfIdentification();
//
//        typeOfIdentification.setCode(TypeOfIdentification.CodeEnum.valueOf(Constants.TYPE_PASSPORT));
//        typeOfIdentification.setName(Constants.NAME_PASSPORT);
//
//        if(customerEntity.getGender().equals("F")){
//            gender.setCode(Gender.CodeEnum.valueOf(Constants.GENDER_F));
//        }
//        if(customerEntity.getGender().equals("M")){
//            gender.setCode(Gender.CodeEnum.valueOf(Constants.GENDER_M));
//        }
//
//        if(customerEntity.getIdentification().length() == 10){
//            typeOfIdentification.setCode(TypeOfIdentification.CodeEnum.valueOf(Constants.TYPE_ID_CARD));
//            typeOfIdentification.setName(Constants.NAME_ID_CARD);
//        }
//        if(customerEntity.getIdentification().length() == 13){
//            typeOfIdentification.setCode(TypeOfIdentification.CodeEnum.valueOf(Constants.TYPE_RUC));
//            typeOfIdentification.setName(Constants.NAME_RUC);
//        }
//        customer.setGender(gender);
//        customer.getIdentification().setType(typeOfIdentification);
//    }

    @Mapping(target = "phoneAddress.description", source = "phone")
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "identification.identifier.value", source = "identification")
    @Mapping(target = "address.value", source = "address")
    @Mapping(target = "status.code", source = "status")
    com.challenge.services.domain.dto.Customer mapperToCustomerDto(com.challenge.services.infrastructure.output.repository.entity.Customer customer);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "phone", source = "phoneAddress.description")
//    @Mapping(target = "gender", source = "gender.value")
//    @Mapping(target = "identification", source = "identification.identifier.value")
//    @Mapping(target = "address", source = "address.value")
//    @Mapping(target = "status", source = "status.code")
//    com.challenge.services.infrastructure.output.repository.entity.Customer mapperToCustomerEntity(PostCustomerRequest postCustomerRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phone", source = "phoneAddress.description")
    @Mapping(target = "gender", source = "gender.value")
    @Mapping(target = "identification", source = "identification.identifier.value")
    @Mapping(target = "address", source = "address.value")
    @Mapping(target = "status", source = "status.code")
    Customer mapperCustomerDtoToCustomerEntity(com.challenge.services.domain.dto.Customer customer);



//    @Mapping(target = "password", ignore = true)
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "age", source = "age")
//    @Mapping(target = "status", source = "status.code")
//    @Mapping(target = "gender", source = "gender.code")
//    @Mapping(target = "identification", source = "identification.identifier.value")
//    @Mapping(target = "address", source = "address.value")
//    @Mapping(target = "phone", source = "phoneAddress.description")
//    Customer mapperPutCustomerToCustomerEntity(PutCustomerRequest putCustomerRequest);

    @Mapping(target = "phone", source = "phoneAddress.description")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gender", source = "gender.value")
    @Mapping(target = "identification", source = "identification.identifier.value")
    @Mapping(target = "address", source = "address.value")
    @Mapping(target = "status", source = "status.code")
    Customer mapperPutCustomerToCustomerEntity(com.challenge.services.domain.dto.Customer customer);


//    @Mapping(target = "phone", ignore = true)
//    @Mapping(target = "name", ignore = true)
//    @Mapping(target = "identification", ignore = true)
//    @Mapping(target = "gender", ignore = true)
//    @Mapping(target = "age", ignore = true)
//    @Mapping(target = "address", ignore = true)
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "status", source = "status.code")
//    Customer mapperPatchCustomerToCustomerEntity(PatchCustomerRequest patchCustomerRequest);


    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "identification", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status.code")
    Customer mapperPatchCustomerToCustomerEntity(com.challenge.services.domain.dto.Customer customer);

}
