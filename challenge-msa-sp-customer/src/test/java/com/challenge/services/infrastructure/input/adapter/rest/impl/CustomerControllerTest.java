package com.challenge.services.infrastructure.input.adapter.rest.impl;

import com.challenge.services.application.service.CustomerServiceImpl;
import com.challenge.services.input.server.models.PostCustomerRequest;
import com.challenge.services.util.MockObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(value = CustomerController.class)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @MockBean
    private CustomerServiceImpl customerService;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void postCustomerSuccess() {
        when(customerService.createCustomer(any())).thenReturn(Mono.empty());

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/support/customers")

                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(MockObject.buildPostCustomerRequest()), PostCustomerRequest.class)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CREATED);
    }


    @Test
    void getServiceByFilterSuccess() {
        when(customerService.getCustomerByFilter(anyString())).thenReturn(Flux.just(MockObject.buildGetCustomer()));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/support/customers")
                        .queryParam("customerId", "2")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }

}