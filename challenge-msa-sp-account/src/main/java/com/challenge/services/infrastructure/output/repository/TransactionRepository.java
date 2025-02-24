package com.challenge.services.infrastructure.output.repository;

import com.challenge.services.infrastructure.output.repository.entity.RepostEntity;
import com.challenge.services.infrastructure.output.repository.entity.TransactionEntity;
import com.challenge.services.input.server.models.TransactionReport;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TransactionRepository {
    Mono<Void> saveTransaction(TransactionEntity transactionEntity);

    Flux<TransactionEntity> findAll();

    Mono<Void> deleteTransaction(String transactionId);

    Mono<Void> updateTransaction(String transactionId, TransactionEntity transactionEntity);

    Mono<Void> patchTransaction(String transactionId, TransactionEntity transactionEntity);

    Flux<RepostEntity> getTransactionReport(String accountNumber, String startDate, String endDate);

}
