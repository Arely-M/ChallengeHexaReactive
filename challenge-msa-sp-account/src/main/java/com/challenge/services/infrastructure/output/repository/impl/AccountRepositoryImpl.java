package com.challenge.services.infrastructure.output.repository.impl;

import com.challenge.services.infrastructure.exception.AccountException;
import com.challenge.services.infrastructure.output.repository.AccountReactiveRepository;
import com.challenge.services.infrastructure.output.repository.AccountRepository;
import com.challenge.services.infrastructure.output.repository.entity.Account;
import com.challenge.services.input.clientSpTransaction.TransactionApi;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.challenge.services.infrastructure.input.adapter.rest.error.resolver.DefaultError.*;

@Repository
@RequiredArgsConstructor
@Slf4j
@Generated
public class AccountRepositoryImpl implements AccountRepository {
    private final AccountReactiveRepository accountReactiveRepository;
    private final TransactionApi transactionApi;


    @Override
    public Mono<Void> saveAccount(Account account) {
        log.info("|---> saveAccount in repository");
        return accountReactiveRepository.save(account)
                .onErrorMap(error -> {
                    log.error("<---| saveAccount - Error al guardar la cuenta y el error es: [{}]", error.getMessage());
                    return new AccountException(error_003_Duplicate);
                })
                .doOnSuccess(response -> log.info("<---| saveAccount finished successfully"))
                .then();
    }

    @Override
    public Flux<Account> findAllAccounts() {
        log.info("|---> findAllAccounts account in repository");
        return accountReactiveRepository.findAll()
                .doOnError(error -> log.error("<---| findAllCustomer - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));
    }

    @Override
    public Mono<Account> findByAccountNumber(String accountNumber) {
        log.info("|---> findByAccountNumber account in repository");
        return accountReactiveRepository.findByAccountNumber(accountNumber)
                .doOnError(error -> log.error("<---| findByAccountNumber - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));
    }

    @Override
    public Mono<Account> findByAccountId(String accountId) {
        log.info("|---> findByAccountId account in repository");
        return accountReactiveRepository.findById(Integer.valueOf(accountId))
                .doOnSuccess(response -> log.info("<---| findByAccountId account finished successfully"))
                .doOnError(error -> log.error("<---| findByAccountId account - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));
    }

    @Override
    public Mono<Void> deleteAccount(String accountId) {
        log.info("|---> deleteAccount in repository");
        return transactionApi.getTransactionByFilter(accountId)
                .flatMap(transaction -> {
                    log.error("Transaction found with account ID: {}", accountId);
                    return Mono.error(new AccountException(error_007_Have_Transactions));
                })
                .switchIfEmpty(accountReactiveRepository.deleteById(Integer.valueOf(accountId))
                        .then(Mono.fromRunnable(() -> log.info("<---| deleteAccount finished successfully"))))
                .doOnError(error -> log.error("<---| deleteAccount - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()))
                .then();
    }

    @Override
    public Mono<Void> updateAccount(String accountId, Account account) {
        log.info("|---> updateAccount in repository");
        return accountReactiveRepository.findById(Integer.valueOf(accountId))
                .switchIfEmpty(Mono.error(new AccountException()))
                .onErrorMap(error -> {
                    log.error("<---| updateAccount - Error al buscar la cuenta con el id: [{}] y el error es: [{}]", accountId,
                            error.getMessage());
                    return new AccountException(error_002_Account_Not_Fount);
                })
                .flatMap(existingAccount -> {
                    existingAccount.setAccountNumber(account.getAccountNumber());
                    existingAccount.setAccountType(account.getAccountType());
                    existingAccount.setInitialBalance(account.getInitialBalance());
                    existingAccount.setStatus(account.getStatus());
                    existingAccount.setCustomerId(account.getCustomerId());
                    return accountReactiveRepository.save(existingAccount);
                })
                .then();
    }

    @Override
    public Mono<Void> updatePartialAccount(String accountId, Account account) {
        log.info("|---> updatePartialAccount in repository");
        return accountReactiveRepository.findById(Integer.valueOf(accountId))
                .switchIfEmpty(Mono.error(new AccountException()))
                .onErrorMap(error -> {
                    log.error("<---| updatePartialAccount - Error al buscar la cuenta con el id: [{}] y el error es: [{}]", accountId,
                            error.getMessage());
                    return new AccountException(error_002_Account_Not_Fount);
                })
                .flatMap(existingAccount -> {
                    existingAccount.setStatus(account.getStatus());
                    return accountReactiveRepository.save(existingAccount);
                })
                .then();
    }

}
