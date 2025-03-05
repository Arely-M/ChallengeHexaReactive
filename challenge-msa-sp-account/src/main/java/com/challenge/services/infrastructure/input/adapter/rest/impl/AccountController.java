package com.challenge.services.infrastructure.input.adapter.rest.impl;

import com.challenge.services.application.input.port.AccountService;
import com.challenge.services.infrastructure.input.adapter.rest.mapper.AccountMapper;
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
public class AccountController implements SupportApi {
    private final AccountService accountService;

    @Override
    public Mono<ResponseEntity<Void>> deleteAccount(String accountId, ServerWebExchange exchange) {
        log.info("|-> SP deleteAccount started");
        return accountService.deleteAccount(accountId)
                .doOnSuccess(response -> log.info("<-| SP deleteAccount finished successfully"))
                .doOnError(error -> log.error("<-| SP deleteAccount finished with error {}", error.getMessage()))
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<Account>>> getAccountByFilter(String accountNumber, ServerWebExchange exchange) {
        log.info("|-> SP getAccountByFilter started");
        return accountService.getAccountByFilter(accountNumber)
                .map(AccountMapper.INSTANCE::mapperToAccountResponse)
                .collectList()
                .map(accounts -> ResponseEntity.ok(Flux.fromIterable(accounts)))
                .doOnSuccess(response -> log.info("<-| SP getAccountByFilter finished successfully"))
                .doOnError(error -> log.error("<-| SP getAccountByFilter finished with error {}", error.getMessage()));
    }

    @Override
    public Mono<ResponseEntity<GetAccountByIdResponse>> getAccountById(String accountId, ServerWebExchange exchange) {
        log.info("|-> SP getAccountById started");
        return accountService.getAccountById(accountId)
                .map(AccountMapper.INSTANCE::mapperToGetAccountByIdResponse)
                .map(ResponseEntity::ok)
                .doOnSuccess(response -> log.info("<-| SP getAccountById finished successfully"))
                .doOnError(error -> log.error("<-| SP getAccountById finished with error {}", error.getMessage()));
    }

    @Override
    public Mono<ResponseEntity<Void>> patchAccount(String accountId, Mono<PatchAccountRequest> patchAccountRequestMono, ServerWebExchange exchange) {
        log.info("|-> SP patchAccount started");
        return patchAccountRequestMono
                .flatMap(patchAccountRequest -> accountService.patchAccount(accountId, AccountMapper.INSTANCE.mapperPatchAccountRequestToAccountDto(patchAccountRequest)))
                .doOnSuccess(response -> log.info("<-| SP patchAccount finished successfully"))
                .doOnError(error -> log.error("<-| SP patchAccount finished with error {}", error.getMessage()))
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> postAccount(Mono<PostAccountRequest> postAccountRequestMono, ServerWebExchange exchange) {
        log.info("|-> SP postAccount started");
        return postAccountRequestMono
                .flatMap(postAccountRequest -> accountService.createAccount(AccountMapper.INSTANCE.mapperPostAccountRequestToAccountDto(postAccountRequest)))
                .doOnSuccess(response -> log.info("<-| SP postAccount finished successfully"))
                .doOnError(error -> log.error("<-| SP postAccount finished with error {}", error.getMessage()))
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> putAccount(String accountId, Mono<PutAccountRequest> putAccountRequestMono, ServerWebExchange exchange) {
        log.info("|-> SP putAccount started");
        return putAccountRequestMono
                .flatMap(putAccountRequest -> accountService.putAccount(accountId, AccountMapper.INSTANCE.mapperPutAccountRequestToAccountDto(putAccountRequest)))
                .doOnSuccess(response -> log.info("<-| SP putAccount finished successfully"))
                .doOnError(error -> log.error("<-| SP putAccount finished with error {}", error.getMessage()))
                .then(Mono.just(ResponseEntity.ok().build()));
    }

}
