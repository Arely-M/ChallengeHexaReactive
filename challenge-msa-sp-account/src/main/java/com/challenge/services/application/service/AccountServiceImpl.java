package com.challenge.services.application.service;

import com.challenge.services.application.input.port.AccountService;
import com.challenge.services.application.output.port.RepositoryPort;
import com.challenge.services.domain.dto.Account;
import com.challenge.services.input.clientSpTransaction.TransactionApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final RepositoryPort repositoryPort;

    @Override
    public Mono<Void> createAccount(com.challenge.services.domain.dto.Account account) {
        log.info("|--> createAccount start");
        return repositoryPort.createAccount(account)
                .doOnSuccess(response -> log.info("<--| createAccount finished successfully"))
                .doOnError(error -> log.error("<--| createAccount finished with error {}", error.getMessage()))
                .then();
    }

    @Override
    public Flux<Account> getAccountByFilter(String accountNumber) {
        log.info("|--> getAccountByFilter start");
        return repositoryPort.getAccountByFilter(accountNumber)
                .doOnNext(response -> log.info("<--| getAccountByFilter finished successfully"))
                .doOnError(error -> log.error("<--| getAccountByFilter finished with error {}", error.getMessage()));
    }

    @Override
    public Mono<Void> deleteAccount(String accountId) {
        log.info("|--> deleteAccount start");
        return repositoryPort.deleteAccount(accountId)
                .doOnSuccess(response -> log.info("<--| deleteAccount finished successfully"))
                .doOnError(error -> log.error("<--| deleteAccount finished with error {}", error.getMessage()));
    }

    @Override
    public Mono<Void> putAccount(String accountId, Account account) {
        log.info("|--> putAccount start");
        return repositoryPort.putAccount(accountId, account)
                .doOnSuccess(response -> log.info("<--| putAccount finished successfully"))
                .doOnError(error -> log.error("<--| putAccount finished with error {}", error.getMessage()));
    }

    @Override
    public Mono<Void> patchAccount(String accountId, Account account) {
        log.info("|--> patchAccount start");
        return repositoryPort.patchAccount(accountId, account)
                .doOnSuccess(response -> log.info("<--| patchAccount finished successfully"))
                .doOnError(error -> log.error("<--| patchAccount finished with error {}", error.getMessage()));
    }

    @Override
    public Mono<Account> getAccountById(String accountId) {
        log.info("|--> getAccountById start");
        return repositoryPort.getAccountById(accountId)
                .doOnNext(response -> log.info("<--| getAccountById finished successfully"))
                .doOnError(error -> log.error("<--| getAccountById finished with error {}", error.getMessage()));
    }
}
