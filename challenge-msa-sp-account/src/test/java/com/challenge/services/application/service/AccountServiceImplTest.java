package com.challenge.services.application.service;

import com.challenge.services.application.output.port.RepositoryPort;
import com.challenge.services.domain.dto.Account;
import com.challenge.services.input.server.models.PostAccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {
    @Mock
    private RepositoryPort repositoryPort;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveAccounWhenValidInput() {
        Account request = new Account();
        when(repositoryPort.createAccount(any(Account.class))).thenReturn(Mono.empty());

        Mono<Void> result = accountService.createAccount(request);

        StepVerifier.create(result)
                .verifyComplete();

        verify(repositoryPort, times(1)).createAccount(any(Account.class));
    }

    @Test
    void shouldReturnAccountWhenValidInput() {
        Account request = new Account();
        when(repositoryPort.getAccountByFilter(anyString(), anyString())).thenReturn(Flux.just(request));

        Mono<Void> result = accountService.getAccountByFilter(anyString(), anyString()).then();

        StepVerifier.create(result)
                .verifyComplete();

        verify(repositoryPort, times(1)).getAccountByFilter(anyString(), anyString());
    }
}