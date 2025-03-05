package com.challenge.services.infrastructure.output.repository;

import com.challenge.services.infrastructure.output.repository.entity.Account;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface AccountRepository{

    Mono<Void> saveAccount(Account account);

    Mono<Account> findByAccountId(String accountId);

    Mono<Void> deleteAccount(String accountId);

    Mono<Void> updateAccount(String accountId, Account account);

    Mono<Void> updatePartialAccount(String accountId, Account account);

    Flux<Account> findAllAccounts();

    Mono<Account> findByAccountNumber(String accountNumber);
}
