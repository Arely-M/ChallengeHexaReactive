package com.challenge.services.application.service;

import com.challenge.services.application.output.port.RepositoryPort;
import com.challenge.services.domain.dto.Customer;
import com.challenge.services.input.server.models.PatchCustomerRequest;
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
        Customer request = new Customer();
        when(repositoryPort.createCustomer(any(Customer.class))).thenReturn(Mono.empty());

        Mono<Void> result = customerService.createCustomer(request);

        StepVerifier.create(result)
                .verifyComplete();

        verify(repositoryPort, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    void shouldReturnCustomerWhenValidInput() {
        Customer request = new Customer();
        when(repositoryPort.getCustomerByFilter(anyString())).thenReturn(Flux.just(request));

        Mono<Void> result = customerService.getCustomerByFilter(CUSTOMER_ID).then();

        StepVerifier.create(result)
                .verifyComplete();

        verify(repositoryPort, times(1)).getCustomerByFilter(anyString());
    }

    @Test
    void shouldDeleteCustomerWhenValidInput() {
        when(repositoryPort.deleteCustomer(anyString())).thenReturn(Mono.empty());

        Mono<Void> result = customerService.deleteCustomer(CUSTOMER_ID);

        StepVerifier.create(result)
                .verifyComplete();

        verify(repositoryPort, times(1)).deleteCustomer(anyString());
    }

    @Test
    void shouldPutCustomerWhenValidInput() {
        Customer request = new Customer();
        when(repositoryPort.putCustomer(anyString(), any())).thenReturn(Mono.empty());

        Mono<Void> result = customerService.putCustomer(CUSTOMER_ID, request);

        StepVerifier.create(result)
                .verifyComplete();

        verify(repositoryPort, times(1)).putCustomer(anyString(), any());
    }


    @Test
    void shouldPatchCustomerWhenValidInput() {
        Customer request = new Customer();
        when(repositoryPort.patchCustomer(anyString(), any())).thenReturn(Mono.empty());

        Mono<Void> result = customerService.patchCustomer(CUSTOMER_ID, request);

        StepVerifier.create(result)
                .verifyComplete();

        verify(repositoryPort, times(1)).patchCustomer(anyString(), any());
    }
}