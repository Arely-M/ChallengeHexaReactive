package com.challenge.services.application.service;

import com.challenge.services.application.output.port.RepositoryPort;
import com.challenge.services.input.server.models.Customer;
import com.challenge.services.input.server.models.PatchCustomerRequest;
import com.challenge.services.input.server.models.PostCustomerRequest;
import com.challenge.services.input.server.models.PutCustomerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {
    @Mock
    private RepositoryPort repositoryPort;

    @InjectMocks
    private CustomerServiceImpl customerService;

    public static final String CUSTOMER_ID = "customerId";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveCustomerWhenValidInput() {
        // Configuración de la simulación
        PostCustomerRequest request = new PostCustomerRequest();
        when(repositoryPort.createCustomer(any(PostCustomerRequest.class))).thenReturn(Mono.empty());

        // Ejecución
        Mono<Void> result = customerService.createCustomer(Mono.just(request));

        // Validación
        StepVerifier.create(result)
                .verifyComplete();

        verify(repositoryPort, times(1)).createCustomer(any(PostCustomerRequest.class));
    }

    @Test
    void shouldReturnCustomerWhenValidInput() {
        // Configuración de la simulación
        Customer request = new Customer();
        when(repositoryPort.getCustomerByFilter(anyString())).thenReturn(Flux.just(request));

        // Ejecución
        Mono<Void> result = customerService.getCustomerByFilter(CUSTOMER_ID).then();

        // Validación
        StepVerifier.create(result)
                .verifyComplete();

        verify(repositoryPort, times(1)).getCustomerByFilter(anyString());
    }

    @Test
    void shouldDeleteCustomerWhenValidInput() {
        // Configuración de la simulación
        when(repositoryPort.deleteCustomer(anyString())).thenReturn(Mono.empty());

        // Ejecución
        Mono<Void> result = customerService.deleteCustomer(CUSTOMER_ID);

        // Validación
        StepVerifier.create(result)
                .verifyComplete();

        verify(repositoryPort, times(1)).deleteCustomer(anyString());
    }

    @Test
    void shouldPutCustomerWhenValidInput() {
        // Configuración de la simulación
        PutCustomerRequest request = new PutCustomerRequest();
        when(repositoryPort.putCustomer(anyString(), any(PutCustomerRequest.class))).thenReturn(Mono.empty());

        // Ejecución
        Mono<Void> result = customerService.putCustomer(CUSTOMER_ID, Mono.just(request));

        // Validación
        StepVerifier.create(result)
                .verifyComplete();

        verify(repositoryPort, times(1)).putCustomer(anyString(), any(PutCustomerRequest.class));
    }


    @Test
    void shouldPatchCustomerWhenValidInput() {
        // Configuración de la simulación
        PatchCustomerRequest request = new PatchCustomerRequest();
        when(repositoryPort.patchCustomer(anyString(), any(PatchCustomerRequest.class))).thenReturn(Mono.empty());

        // Ejecución
        Mono<Void> result = customerService.patchCustomer(CUSTOMER_ID, Mono.just(request));

        // Validación
        StepVerifier.create(result)
                .verifyComplete();

        verify(repositoryPort, times(1)).patchCustomer(anyString(), any(PatchCustomerRequest.class));
    }
}