package com.challenge.services.application.output.port;

import com.challenge.services.domain.dto.Customer;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface RepositoryPort {
    Mono<Customer> createCustomer(Customer customer);

    Flux<Customer> getCustomerByFilter(String customerId);

    Mono<Void> deleteCustomer(String customerId);

    Mono<Void> putCustomer(String customerId, Customer customer);

    Mono<Void> patchCustomer(String customerId, Customer customer);
}
