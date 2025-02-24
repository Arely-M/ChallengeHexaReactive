package com.challenge.services.infrastructure.output.adapter.mapper;

import com.challenge.services.infrastructure.output.repository.entity.AccountEntity;
import com.challenge.services.infrastructure.output.repository.entity.RepostEntity;
import com.challenge.services.infrastructure.output.repository.entity.TransactionEntity;
import com.challenge.services.infrastructure.output.util.Constants;
import com.challenge.services.input.server.models.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Random;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostgreSQLRepositoryAdapterMapper {

    PostgreSQLRepositoryAdapterMapper INSTANCE = Mappers.getMapper(PostgreSQLRepositoryAdapterMapper.class);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status.code")
    @Mapping(target = "accountNumber", expression = "java(accountNumberRandom())")
    @Mapping(target = "accountType", source = "type.description")
    AccountEntity mapperToAccountEntity(PostAccountRequest postAccountRequest);

    default String accountNumberRandom() {
        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();
        randomNumber.append(random.nextInt(9) + 1);

        for (int i = 0; i < 9; i++) {
            randomNumber.append(random.nextInt(10));
        }

        return randomNumber.toString();
    }

    @Mapping(target = "accountId", source = "id")
    @Mapping(target = "type.description", source = "accountType")
    @Mapping(target = "status.code", source = "status")
    Account mapperToAccount(AccountEntity accountEntity);

    @AfterMapping
    default void afterAccount(@MappingTarget Account account, AccountEntity accountEntity) {
        String accountType = accountEntity.getAccountType();
        String typeCode = mapAccountTypeToCode(accountType);
        account.getType().setCode(typeCode);
    }

    @Mapping(target = "accountId", source = "id")
    @Mapping(target = "type.description", source = "accountType")
    @Mapping(target = "status.code", source = "status")
    GetAccountByIdResponse mapperToGetAccountById(AccountEntity accountEntity);

    @AfterMapping
    default void afterGetAccountByIdResponse(@MappingTarget GetAccountByIdResponse getAccountByIdResponse, AccountEntity accountEntity) {
        String accountType = accountEntity.getAccountType();
        String typeCode = mapAccountTypeToCode(accountType);
        getAccountByIdResponse.getType().setCode(typeCode);
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountType", source = "type.description")
    @Mapping(target = "status", source = "status.code")
    AccountEntity mapperPutAccountToAccountEntity(PutAccountRequest putAccountRequest);

    @Mapping(target = "initialBalance", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "accountType", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "status", source = "status.code")
    AccountEntity mapperPatchAccountToAccountEntity(PatchAccountRequest patchAccountRequest);



    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transactionType", source = "postAccountTransactionRequest.type.description")
    @Mapping(target = "date", expression = "java(currentDate())")
    @Mapping(target = "balance", source = "accountEntity.initialBalance")
    @Mapping(target = "accountId", source = "accountEntity.id")
    TransactionEntity mapperToTransactionEntity(PostAccountTransactionRequest postAccountTransactionRequest, AccountEntity accountEntity);

    default String currentDate () {
        return LocalDate.now().toString();
    }

    @Mapping(target = "transactionId", source = "id")
    @Mapping(target = "type.description", source = "transactionEntity.transactionType")
    Transaction mapperToTransaction(TransactionEntity transactionEntity);

    @AfterMapping
    default void afterTransaction(@MappingTarget Transaction transaction, TransactionEntity transactionEntity) {
        String transactionType = transactionEntity.getTransactionType();
        String typeCode = mapTransactionTypeToCode(transactionType);
        transaction.getType().setCode(typeCode);
    }

    private String mapTransactionTypeToCode(String transactionType) {
        switch (transactionType) {
            case Constants.WITHDRAWAL:
                return Constants.TYPE_WITHDRAWAL;
            case Constants.DEPOSIT:
                return Constants.TYPE_DEPOSIT;
            default:
                throw new IllegalArgumentException("Unknown transaction type: " + transactionType);
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transactionType", source = "type.description")
    TransactionEntity mapperPutAccountTransactionRequestToTransactionEntity(PutAccountTransactionRequest putAccountTransactionRequest);

    @Mapping(target = "transactionType", source = "type.description")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    TransactionEntity mapperPatchAccountTransactionToTransactionEntity(PatchAccountTransactionRequest patchAccountTransactionRequest);

    @Mapping(target = "status.code", source = "status")
    TransactionReport mapperToTransactionReport(RepostEntity repostEntity);
}
