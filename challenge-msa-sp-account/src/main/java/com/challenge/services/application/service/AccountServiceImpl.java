package com.challenge.services.application.service;

import com.challenge.services.application.input.port.AccountService;
import com.challenge.services.application.output.port.RepositoryPort;
import com.challenge.services.input.server.models.*;
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
    public Mono<Void> createAccount(Mono<PostAccountRequest> postAccountRequestMono) {
        log.info("|--> createAccount start");
        return postAccountRequestMono
                .flatMap(repositoryPort::createAccount)
                .doOnSuccess(response -> log.info("<--| createAccount finished successfully"))
                .doOnError(error -> log.error("<--| createAccount finished with error", error))
                .then();
    }

    @Override
    public Flux<Account> getAccountByFilter(String accountNumber) {
        log.info("|--> getAccountByFilter start");
        return repositoryPort.getAccountByFilter(accountNumber)
                .doOnNext(response -> log.info("<--| getAccountByFilter finished successfully"))
                .doOnError(error -> log.error("<--| getAccountByFilter finished with error", error));
    }

    @Override
    public Mono<Void> deleteAccount(String accountId) {
        log.info("|--> deleteAccount start");
        return repositoryPort.deleteAccount(accountId)
                .doOnSuccess(response -> log.info("<--| deleteAccount finished successfully"))
                .doOnError(error -> log.error("<--| deleteAccount finished with error", error));
    }

    @Override
    public Mono<Void> putAccount(String accountId, Mono<PutAccountRequest> putAccountRequestMono) {
        log.info("|--> putAccount start");
        return putAccountRequestMono
                .flatMap(putAccountRequest -> repositoryPort.putAccount(accountId, putAccountRequest))
                .doOnSuccess(response -> log.info("<--| putAccount finished successfully"))
                .doOnError(error -> log.error("<--| putAccount finished with error", error));
    }

    @Override
    public Mono<Void> patchAccount(String accountId, Mono<PatchAccountRequest> patchAccountRequestMono) {
        log.info("|--> patchAccount start");
        return patchAccountRequestMono
                .flatMap(patchAccountRequest -> repositoryPort.patchAccount(accountId, patchAccountRequest))
                .doOnSuccess(response -> log.info("<--| patchAccount finished successfully"))
                .doOnError(error -> log.error("<--| patchAccount finished with error", error));
    }

    @Override
    public Mono<GetAccountByIdResponse> getAccountById(String accountId) {
        log.info("|--> getAccountById start");
        return repositoryPort.getAccountById(accountId)
                .doOnNext(response -> log.info("<--| getAccountById finished successfully"))
                .doOnError(error -> log.error("<--| getAccountById finished with error", error));
    }
}
