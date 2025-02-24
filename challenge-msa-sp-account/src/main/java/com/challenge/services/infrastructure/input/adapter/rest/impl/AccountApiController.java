package com.challenge.services.infrastructure.input.adapter.rest.impl;

import com.challenge.services.application.input.port.AccountService;
import com.challenge.services.application.input.port.TransactionService;
import com.challenge.services.input.server.SupportApi;
import com.challenge.services.input.server.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountApiController implements SupportApi {
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Override
    public Mono<ResponseEntity<Void>> deleteAccount(String accountId, ServerWebExchange exchange) {
        log.info("|-> SP deleteAccount started");
        return accountService.deleteAccount(accountId)
                .doOnSuccess(response -> log.info("<-| SP deleteAccount finished successfully"))
                .doOnError(error -> log.error("<-| SP deleteAccount finished with error", error))
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteAccountTransaction(String transactionId, ServerWebExchange exchange) {
        log.info("|-> SP deleteAccountTransaction started");
        return transactionService.deleteTransaction(transactionId)
                .doOnSuccess(response -> log.info("<-| SP deleteAccountTransaction finished successfully"))
                .doOnError(error -> log.error("<-| SP deleteAccountTransaction finished with error", error))
                .then(Mono.just(ResponseEntity.ok().build()));
    }


    @Override
    public Mono<ResponseEntity<Flux<Account>>> getAccountByFilter(String accountNumber, ServerWebExchange exchange) {
        log.info("|-> SP getAccountByFilter started");
        return accountService.getAccountByFilter(accountNumber)
                .collectList()
                .map(accounts -> ResponseEntity.ok(Flux.fromIterable(accounts)))
                .doOnSuccess(response -> log.info("<-| SP getAccountByFilter finished successfully"))
                .doOnError(error -> log.error("<-| SP getAccountByFilter finished with error", error));
    }

    @Override
    public Mono<ResponseEntity<GetAccountByIdResponse>> getAccountById(String accountId, ServerWebExchange exchange) {
        log.info("|-> SP getAccountById started");
        return accountService.getAccountById(accountId)
                .map(ResponseEntity::ok)
                .doOnSuccess(response -> log.info("<-| SP getAccountById finished successfully"))
                .doOnError(error -> log.error("<-| SP getAccountById finished with error", error));
    }

    @Override
    public Mono<ResponseEntity<Flux<Transaction>>> getAccountTransactionByFilter(String accountId, ServerWebExchange exchange) {
        log.info("|-> SP getAccountTransactionByFilter started");
        return transactionService.getTransactionByFilter()
                .collectList()
                .map(transactions -> ResponseEntity.ok(Flux.fromIterable(transactions)))
                .doOnSuccess(response -> log.info("<-| SP getAccountTransactionByFilter finished successfully"))
                .doOnError(error -> log.error("<-| SP getAccountTransactionByFilter finished with error", error));
    }

    @Override
    public Mono<ResponseEntity<Flux<TransactionReport>>> getAccountTransactionReport(String accountNumber, String startDate, String endDate, ServerWebExchange exchange) {
        log.info("|-> SP getAccountTransactionReport started");
        return transactionService.getAccountTransactionReport(accountNumber, startDate, endDate)
                .collectList()
                .map(transactionReports -> ResponseEntity.ok(Flux.fromIterable(transactionReports)))
                .doOnSuccess(response -> log.info("<-| SP getAccountTransactionReport finished successfully"))
                .doOnError(error -> log.error("<-| SP getAccountTransactionReport finished with error", error));
    }

    @Override
    public Mono<ResponseEntity<Void>> patchAccount(String accountId, Mono<PatchAccountRequest> patchAccountRequest, ServerWebExchange exchange) {
        log.info("|-> SP patchAccount started");
        return accountService.patchAccount(accountId, patchAccountRequest)
                .doOnSuccess(response -> log.info("<-| SP patchAccount finished successfully"))
                .doOnError(error -> log.error("<-| SP patchAccount finished with error", error))
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> patchAccountTransaction(String transactionId, Mono<PatchAccountTransactionRequest> patchAccountTransactionRequest, ServerWebExchange exchange) {
        log.info("|-> SP patchAccountTransaction started");
        return transactionService.patchAccountTransaction(transactionId, patchAccountTransactionRequest)
                .doOnSuccess(response -> log.info("<-| SP patchAccountTransaction finished successfully"))
                .doOnError(error -> log.error("<-| SP patchAccountTransaction finished with error", error))
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> postAccount(Mono<PostAccountRequest> postAccountRequest, ServerWebExchange exchange) {
        log.info("|-> SP postAccount started");
        return accountService.createAccount(postAccountRequest)
                .doOnSuccess(response -> log.info("<-| SP postAccount finished successfully"))
                .doOnError(error -> log.error("<-| SP postAccount finished with error", error))
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
    }

    @Override
    public Mono<ResponseEntity<PostAccountTransactionResponse>> postAccountTransaction(String accountNumber, Mono<PostAccountTransactionRequest> postAccountTransactionRequest, ServerWebExchange exchange) {
        log.info("|-> SP postAccountTransaction started");
        return transactionService.createTransaction(accountNumber, postAccountTransactionRequest)
                .doOnSuccess(response -> log.info("<-| SP postAccountTransaction finished successfully"))
                .doOnError(error -> log.error("<-| SP postAccountTransaction finished with error", error))
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> putAccount(String accountId, Mono<PutAccountRequest> putAccountRequest, ServerWebExchange exchange) {
        log.info("|-> SP putAccount started");
        return accountService.putAccount(accountId, putAccountRequest)
                .doOnSuccess(response -> log.info("<-| SP putAccount finished successfully"))
                .doOnError(error -> log.error("<-| SP putAccount finished with error", error))
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> putAccountTransaction(String transactionId, Mono<PutAccountTransactionRequest> putAccountTransactionRequest, ServerWebExchange exchange) {
        log.info("|-> SP putAccountTransaction started");
        return transactionService.putTransaction(transactionId, putAccountTransactionRequest)
                .doOnSuccess(response -> log.info("<-| SP putAccountTransaction finished successfully"))
                .doOnError(error -> log.error("<-| SP putAccountTransaction finished with error", error))
                .then(Mono.just(ResponseEntity.ok().build()));
    }
}
