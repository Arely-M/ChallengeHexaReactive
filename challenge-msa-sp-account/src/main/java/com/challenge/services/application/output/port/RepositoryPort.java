package com.challenge.services.application.output.port;

import com.challenge.services.input.server.models.*;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface RepositoryPort {
    //Account
    Mono<Void> createAccount(PostAccountRequest postAccountRequest);

    Flux<Account> getAccountByFilter(String accountNumber);

    Mono<Void> deleteAccount(String accountId);

    Mono<Void> putAccount(String accountId, PutAccountRequest putAccountRequest);

    Mono<Void> patchAccount(String accountId, PatchAccountRequest patchAccountRequest);

    Mono<GetAccountByIdResponse> getAccountById(String accountId);

    //Transaction
    Mono<Void> createTransaction(String accountNumber, PostAccountTransactionRequest postAccountTransactionRequest);

    Flux<Transaction> getTransactionByFilter();

    Mono<Void> deleteTransaction(String transactionId);

    Mono<Void> putTransaction(String transactionId, PutAccountTransactionRequest putAccountTransactionRequest);

    Mono<Void> patchAccountTransaction(String transactionId, PatchAccountTransactionRequest patchAccountTransactionRequest);

    Flux<TransactionReport> getAccountTransactionReport(String accountNumber, String startDate, String endDate);
}
