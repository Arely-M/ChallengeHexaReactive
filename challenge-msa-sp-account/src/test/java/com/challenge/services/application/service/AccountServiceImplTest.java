package com.challenge.services.application.service;

import com.challenge.services.application.output.port.RepositoryPort;
import com.challenge.services.input.server.models.Account;
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
        // Configuración de la simulación
        PostAccountRequest request = new PostAccountRequest();
        when(repositoryPort.createAccount(any(PostAccountRequest.class))).thenReturn(Mono.empty());

        // Ejecución
        Mono<Void> result = accountService.createAccount(Mono.just(request));

        // Validación
        StepVerifier.create(result)
                .verifyComplete();

        verify(repositoryPort, times(1)).createAccount(any(PostAccountRequest.class));
    }

    @Test
    void shouldReturnAccountWhenValidInput() {
        // Configuración de la simulación
        Account request = new Account();
        when(repositoryPort.getAccountByFilter(anyString())).thenReturn(Flux.just(request));

        // Ejecución
        Mono<Void> result = accountService.getAccountByFilter(anyString()).then();

        // Validación
        StepVerifier.create(result)
                .verifyComplete();

        verify(repositoryPort, times(1)).getAccountByFilter(anyString());
    }
}