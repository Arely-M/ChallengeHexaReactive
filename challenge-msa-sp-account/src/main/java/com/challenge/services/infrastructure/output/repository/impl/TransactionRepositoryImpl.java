package com.challenge.services.infrastructure.output.repository.impl;

import com.challenge.services.infrastructure.exception.TransactionException;
import com.challenge.services.infrastructure.output.repository.ReportReactiveRepository;
import com.challenge.services.infrastructure.output.repository.TransactionReactiveRepository;
import com.challenge.services.infrastructure.output.repository.TransactionRepository;
import com.challenge.services.infrastructure.output.repository.entity.RepostEntity;
import com.challenge.services.infrastructure.output.repository.entity.TransactionEntity;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.challenge.services.infrastructure.input.adapter.rest.error.resolver.DefaultError.error_003_Duplicate;
import static com.challenge.services.infrastructure.input.adapter.rest.error.resolver.DefaultError.error_004_Transaction_Not_Fount;

@Repository
@RequiredArgsConstructor
@Slf4j
@Generated
public class TransactionRepositoryImpl implements TransactionRepository {
    private final TransactionReactiveRepository transactionReactiveRepository;
    private final ReportReactiveRepository reportReactiveRepository;

    @Override
    public Mono<Void> saveTransaction(TransactionEntity transactionEntity) {
        log.info("|---> saveTransaction in repository");
        return transactionReactiveRepository.save(transactionEntity)
                .onErrorMap(error -> {
                    log.error("<---| saveTransaction - Error al guardar la transaccion y el error es: [{}]", error.getMessage());
                    return new TransactionException(error_003_Duplicate);
                })
                .doOnSuccess(response -> log.info("<---| saveTransaction finished successfully"))
                .then();
    }

    @Override
    public Flux<TransactionEntity> findAll() {
        log.info("|---> findAll transaction in repository");
        return transactionReactiveRepository.findAll()
                .doOnNext(response -> log.info("<---| findAll transaction finished successfully"))
                .doOnError(error -> log.error("<---| findAll transaction - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));
    }

    @Override
    public Mono<Void> deleteTransaction(String transactionId) {
        log.info("|---> deleteTransaction in repository");
        return transactionReactiveRepository.deleteById(Integer.valueOf(transactionId))
                .doOnSuccess(response -> log.info("<---| deleteTransaction finished successfully"))
                .doOnError(error -> log.error("<---| deleteTransaction - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));
    }

    @Override
    public Mono<Void> updateTransaction(String transactionId, TransactionEntity transactionEntity) {
        log.info("|---> updateTransaction in repository");
        return transactionReactiveRepository.findById(Integer.valueOf(transactionId))
                .switchIfEmpty(Mono.error(new TransactionException()))
                .onErrorMap(error -> {
                    log.error("<---| updateCustomer - Error al buscar el transacción con el id: [{}] y el error es: [{}]", transactionId,
                            error.getMessage());
                    return new TransactionException(error_004_Transaction_Not_Fount);
                })
                .flatMap(existingTransaction -> {
                    existingTransaction.setDate(transactionEntity.getDate());
                    existingTransaction.setTransactionType(transactionEntity.getTransactionType());
                    existingTransaction.setValue(transactionEntity.getValue());
                    existingTransaction.setBalance(transactionEntity.getBalance());
                    existingTransaction.setAccountId(transactionEntity.getAccountId());
                    return transactionReactiveRepository.save(existingTransaction);
                })
                .then();
    }

    @Override
    public Mono<Void> patchTransaction(String transactionId, TransactionEntity transactionEntity) {
        log.info("|---> patchTransaction in repository");
        return transactionReactiveRepository.findById(Integer.valueOf(transactionId))
                .switchIfEmpty(Mono.error(new TransactionException()))
                .onErrorMap(error -> {
                    log.error("<---| updatePartialCustomer - Error al buscar el transacción con el id: [{}] y el error es: [{}]", transactionId,
                            error.getMessage());
                    return new TransactionException(error_004_Transaction_Not_Fount);
                })
                .flatMap(existingTransaction -> {
                    existingTransaction.setTransactionType(transactionEntity.getTransactionType());
                    existingTransaction.setValue(transactionEntity.getValue());
                    return transactionReactiveRepository.save(existingTransaction);
                })
                .then();
    }

    @Override
    public Flux<RepostEntity> getTransactionReport(String accountNumber, String startDate, String endDate) {
        return reportReactiveRepository.findReport(accountNumber, startDate, endDate);
    }
}
