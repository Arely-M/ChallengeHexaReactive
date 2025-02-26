package com.challenge.services.infrastructure.output.repository;

import com.challenge.services.infrastructure.output.repository.entity.CustomerEntity;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface CustomerRepository {
    Mono<CustomerEntity> saveCustomer(CustomerEntity customerEntity);

    Mono<CustomerEntity> findByCustomerId(String customerId);

    Mono<Void> deleteCustomer(String customerId);

    Mono<Void> updateCustomer(String customerId, CustomerEntity customerEntity);

    Mono<Void> updatePartialCustomer(String customerId, CustomerEntity customerEntity);

    Flux<CustomerEntity> findAllCustomer();
}
