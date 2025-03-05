package com.challenge.services.application.output.port;

import com.challenge.services.domain.dto.Account;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface RepositoryPort {
    Mono<Void> createAccount(Account account);

    Flux<Account> getAccountByFilter(String accountNumber);

    Mono<Void> deleteAccount(String accountId);

    Mono<Void> putAccount(String accountId, Account account);

    Mono<Void> patchAccount(String accountId, Account account);

    Mono<Account> getAccountById(String accountId);
}
