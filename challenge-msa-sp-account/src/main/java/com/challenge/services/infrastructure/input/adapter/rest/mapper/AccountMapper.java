package com.challenge.services.infrastructure.input.adapter.rest.mapper;

import com.challenge.services.domain.dto.Account;
import com.challenge.services.input.server.models.GetAccountByIdResponse;
import com.challenge.services.input.server.models.PatchAccountRequest;
import com.challenge.services.input.server.models.PostAccountRequest;
import com.challenge.services.input.server.models.PutAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "initialBalance", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    Account mapperPostAccountRequestToAccountDto(PostAccountRequest postAccountRequest);

    com.challenge.services.input.server.models.Account mapperToAccountResponse(Account account);

    GetAccountByIdResponse mapperToGetAccountByIdResponse(Account account);

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "initialBalance", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    Account mapperPatchAccountRequestToAccountDto(PatchAccountRequest patchAccountRequest);

    @Mapping(target = "accountId", ignore = true)
    Account mapperPutAccountRequestToAccountDto(PutAccountRequest putAccountRequest);


}
