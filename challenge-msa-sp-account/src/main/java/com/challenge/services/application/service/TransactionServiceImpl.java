package com.challenge.services.application.service;

import com.challenge.services.application.input.port.TransactionService;
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
public class TransactionServiceImpl implements TransactionService {
    private final RepositoryPort repositoryPort;

    @Override
    public Mono<Void> createTransaction(String accountNumber, Mono<PostAccountTransactionRequest> postAccountTransactionRequestMono) {
        log.info("|--> createTransaction start");
        return postAccountTransactionRequestMono
                .flatMap(postAccountTransactionRequest -> repositoryPort.createTransaction(accountNumber, postAccountTransactionRequest))
                .doOnSuccess(response -> log.info("<--| createTransaction finished successfully"))
                .doOnError(error -> log.error("<--| createTransaction finished with error", error))
                .then();
    }

    @Override
    public Flux<Transaction> getTransactionByFilter() {
        log.info("|--> getTransactionByFilter start");
        return repositoryPort.getTransactionByFilter()
                .doOnNext(response -> log.info("<--| getTransactionByFilter finished successfully"))
                .doOnError(error -> log.error("<--| getTransactionByFilter finished with error", error));
    }

    @Override
    public Mono<Void> deleteTransaction(String transactionId) {
        log.info("|--> deleteTransaction start");
        return repositoryPort.deleteTransaction(transactionId)
                .doOnSuccess(response -> log.info("<--| deleteTransaction finished successfully"))
                .doOnError(error -> log.error("<--| deleteTransaction finished with error", error));
    }

    @Override
    public Mono<Void> putTransaction(String transactionId, Mono<PutAccountTransactionRequest> putAccountTransactionRequestMono) {
        log.info("|--> putTransaction start");
        return putAccountTransactionRequestMono
                .flatMap(putAccountTransactionRequest -> repositoryPort.putTransaction(transactionId, putAccountTransactionRequest))
                .doOnSuccess(response -> log.info("<--| putTransaction finished successfully"))
                .doOnError(error -> log.error("<--| putTransaction finished with error", error));
    }

    @Override
    public Mono<Void> patchAccountTransaction(String transactionId, Mono<PatchAccountTransactionRequest> patchAccountTransactionRequestMono) {
        log.info("|--> patchAccountTransaction start");
        return patchAccountTransactionRequestMono
                .flatMap(patchAccountTransactionRequest -> repositoryPort.patchAccountTransaction(transactionId, patchAccountTransactionRequest))
                .doOnSuccess(response -> log.info("<--| patchAccountTransaction finished successfully"))
                .doOnError(error -> log.error("<--| patchAccountTransaction finished with error", error));
    }

    @Override
    public Flux<TransactionReport> getAccountTransactionReport(String accountNumber, String startDate, String endDate) {
        log.info("|--> getAccountTransactionReport start");
        return repositoryPort.getAccountTransactionReport(accountNumber, startDate, endDate)
                .doOnNext(response -> log.info("<--| getAccountTransactionReport finished successfully"))
                .doOnError(error -> log.error("<--| getAccountTransactionReport finished with error", error));
    }
}
