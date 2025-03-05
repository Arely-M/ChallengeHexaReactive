package com.challenge.services.infrastructure.input.adapter.rest.mapper;

import com.challenge.services.domain.dto.Report;
import com.challenge.services.input.server.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    com.challenge.services.domain.dto.Transaction mapperPostTransactionRequestToTransactionDto(PostTransactionRequest postTransactionRequest);

    Transaction mapperTransactionDtoToTransactionResponse(com.challenge.services.domain.dto.Transaction transaction);

    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    com.challenge.services.domain.dto.Transaction mapperPatchTransactionRequestToTransactionDto(PatchTransactionRequest patchTransactionRequest);

    @Mapping(target = "transactionId", ignore = true)
    com.challenge.services.domain.dto.Transaction mapperPutTransactionRequestToTransactionDto(PutTransactionRequest putTransactionRequest);

    TransactionReport mapperReportDtoToTransactionReport(Report report);
}
