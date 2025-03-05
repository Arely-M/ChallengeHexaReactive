package com.challenge.services.application.input.port;

import com.challenge.services.domain.dto.Account;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Validated
public interface AccountService {
    Mono<Void> createAccount(@Valid Account account);

    Flux<Account> getAccountByFilter(String accountNumber);

    Mono<Void> deleteAccount(String accountId);

    Mono<Void> putAccount(String accountId, @Valid Account account);

    Mono<Void> patchAccount(String accountId, @Valid Account account);

    Mono<Account> getAccountById(String accountId);
}
