package com.challenge.services.infrastructure.output.adapter.mapper;

import com.challenge.services.infrastructure.output.repository.entity.Account;
import com.challenge.services.infrastructure.output.util.Constants;
import com.challenge.services.input.server.models.GetAccountByIdResponse;
import com.challenge.services.input.server.models.PatchAccountRequest;
import com.challenge.services.input.server.models.PutAccountRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Random;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostgreSQLRepositoryAdapterMapper {

    PostgreSQLRepositoryAdapterMapper INSTANCE = Mappers.getMapper(PostgreSQLRepositoryAdapterMapper.class);

    @Mapping(target = "status", source = "status.code")
    @Mapping(target = "initialBalance", constant = Constants.INITIAL_BALANCE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountType", source = "type.description")
    @Mapping(target = "accountNumber", expression = "java(accountNumberRandom())")
    Account mapperAccountToAccountEntity(com.challenge.services.domain.dto.Account account);

    default String accountNumberRandom() {
        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();
        randomNumber.append(random.nextInt(9) + 1);

        for (int i = 0; i < 9; i++) {
            randomNumber.append(random.nextInt(10));
        }

        return randomNumber.toString();
    }

    @Mapping(target = "status.code", source = "status")
    @Mapping(target = "type.description", source = "accountType")
    @Mapping(target = "accountId", source = "id")
    com.challenge.services.domain.dto.Account mapperAccountEntityToAccountDto(Account account);

    @AfterMapping
    default void afterAccount(@MappingTarget com.challenge.services.domain.dto.Account account, Account accountEntity) {
        String typeCode = mapAccountTypeToCode(accountEntity.getAccountType());
        account.getType().setCode(typeCode);
    }

    @Mapping(target = "accountId", source = "id")
    @Mapping(target = "type.description", source = "accountType")
    @Mapping(target = "status.code", source = "status")
    com.challenge.services.domain.dto.Account mapperToAccountDtoById(Account account);

    @AfterMapping
    default void afterAccountResponse(@MappingTarget com.challenge.services.domain.dto.Account account, Account accountEntity) {
        String typeCode = mapAccountTypeToCode(accountEntity.getAccountType());
        account.getType().setCode(typeCode);
    }

    private String mapAccountTypeToCode(String accountType) {
        switch (accountType) {
            case Constants.SAVING:
                return Constants.TYPE_SAVING;
            case Constants.CURRENT:
                return Constants.TYPE_CURRENT;
            default:
                throw new IllegalArgumentException("Unknown account type: " + accountType);
        }
    }

    @Mapping(target = "initialBalance", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "accountType", ignore = true)
    @Mapping(target = "status", source = "status.code")
    Account mapperPatchAccountToAccountEntity(com.challenge.services.domain.dto.Account account);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountType", source = "type.description")
    @Mapping(target = "status", source = "status.code")
    Account mapperPutAccountToAccountEntity(com.challenge.services.domain.dto.Account account);

}
