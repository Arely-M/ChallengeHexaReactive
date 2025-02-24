package com.challenge.services.application.output.port;

import com.challenge.services.input.server.models.Customer;
import com.challenge.services.input.server.models.PatchCustomerRequest;
import com.challenge.services.input.server.models.PostCustomerRequest;
import com.challenge.services.input.server.models.PutCustomerRequest;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface RepositoryPort {
    Mono<Void> createCustomer(PostCustomerRequest postCustomerRequest);

    Flux<Customer> getCustomerByFilter(String customerId);

    Mono<Void> deleteCustomer(String customerId);

    Mono<Void> putCustomer(String customerId, PutCustomerRequest putCustomerRequest);

    Mono<Void> patchCustomer(String customerId, PatchCustomerRequest patchCustomerRequest);
}
