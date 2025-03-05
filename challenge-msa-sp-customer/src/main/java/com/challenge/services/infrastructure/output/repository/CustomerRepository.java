package com.challenge.services.infrastructure.output.repository;

import com.challenge.services.infrastructure.output.repository.entity.Customer;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface CustomerRepository {
    Mono<Customer> saveCustomer(Customer customer);

    Mono<Customer> findByCustomerId(String customerId);

    Mono<Void> deleteCustomer(String customerId);

    Mono<Void> updateCustomer(String customerId, Customer customer);

    Mono<Void> updatePartialCustomer(String customerId, Customer customer);

    Flux<Customer> findAllCustomer();
}
