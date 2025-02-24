package com.challenge.services.application.input.port;

import com.challenge.services.input.server.models.*;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface TransactionService {
    Mono<Void> createTransaction(String accountNumber, Mono<PostAccountTransactionRequest> postAccountTransactionRequestMono);

    Flux<Transaction> getTransactionByFilter();

    Mono<Void> deleteTransaction(String transactionId);

    Mono<Void> putTransaction(String transactionId, Mono<PutAccountTransactionRequest> putAccountTransactionRequestMono);

    Mono<Void> patchAccountTransaction(String transactionId, Mono<PatchAccountTransactionRequest> patchAccountTransactionRequest);

    Flux<TransactionReport> getAccountTransactionReport(String accountNumber, String startDate, String endDate);
}
