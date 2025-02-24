package com.challenge.services.infrastructure.output.repository;

import com.challenge.services.infrastructure.output.repository.entity.AccountEntity;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface AccountRepository{

    Mono<Void> saveAccount(AccountEntity accountEntity);

    Mono<AccountEntity> findByAccountId(String accountId);

    Mono<Void> deleteAccount(String accountId);

    Mono<Void> updateAccount(String accountId, AccountEntity accountEntity);

    Mono<Void> updatePartialAccount(String accountId, AccountEntity accountEntity);

    Flux<AccountEntity> findAllAccounts();

    Mono<AccountEntity> findByAccountNumber(String accountNumber);
}
