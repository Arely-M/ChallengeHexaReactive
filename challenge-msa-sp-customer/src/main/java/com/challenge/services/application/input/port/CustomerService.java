package com.challenge.services.application.input.port;

import com.challenge.services.domain.dto.Customer;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface CustomerService {

    Mono<Void> createCustomer(Customer customer);

    Flux<Customer> getCustomerByFilter(String customerId);

    Mono<Void> deleteCustomer(String customerId);

    Mono<Void> putCustomer(String customerId, Customer customer);

    Mono<Void> patchCustomer(String customerId, Customer customer);
}
