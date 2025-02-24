package com.challenge.services.application.input.port;

import com.challenge.services.input.server.models.*;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface AccountService {
    Mono<Void> createAccount(Mono<PostAccountRequest> postAccountRequest);

    Flux<Account> getAccountByFilter(String accountNumber);

    Mono<Void> deleteAccount(String accountId);

    Mono<Void> putAccount(String accountId, Mono<PutAccountRequest> putAccountRequest);

    Mono<Void> patchAccount(String accountId, Mono<PatchAccountRequest> patchAccountRequest);

    Mono<GetAccountByIdResponse> getAccountById(String accountId);
}
