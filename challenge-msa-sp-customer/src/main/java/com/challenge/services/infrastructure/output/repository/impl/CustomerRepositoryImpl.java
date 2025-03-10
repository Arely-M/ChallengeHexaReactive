package com.challenge.services.infrastructure.output.repository.impl;

import com.challenge.services.infrastructure.exception.CustomerException;
import com.challenge.services.infrastructure.output.repository.CustomerReactiveRepository;
import com.challenge.services.infrastructure.output.repository.CustomerRepository;
import com.challenge.services.infrastructure.output.repository.entity.Customer;
import com.challenge.services.input.clientSpAccount.AccountApi;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.challenge.services.infrastructure.input.adapter.rest.error.resolver.DefaultError.error_002_Customer_Not_Fount;
import static com.challenge.services.infrastructure.input.adapter.rest.error.resolver.DefaultError.error_005_Have_Account;

@Repository
@RequiredArgsConstructor
@Slf4j
@Generated
public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerReactiveRepository customerReactiveRepository;
    private final AccountApi accountApi;

    @Override
    public Mono<Customer> saveCustomer(Customer customer) {
        log.info("|---> saveCustomer in repository");
        return customerReactiveRepository.save(customer)
                .onErrorMap(error -> {
                    log.error("<---| saveCustomer - Error al guardar el cliente y el error es: [{}]", error.getMessage());
                    return new CustomerException(error_002_Customer_Not_Fount);
                })
                .doOnSuccess(response -> log.info("<---| saveCustomer finished successfully"));
    }

    @Override
    public Mono<Customer> findByCustomerId(String customerId) {
        log.info("|---> findByCustomerId in repository");
        return Optional.ofNullable(customerId)
                .map(s -> customerReactiveRepository.findById(Integer.valueOf(s)))
                .orElse(Mono.error(new CustomerException()))
                .doOnError(error -> log.error("<---| findByCustomerId - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));

    }

    @Override
    public Mono<Void> deleteCustomer(String customerId) {
        log.info("|---> deleteCustomer in repository");
        return accountApi.getAccountByFilter(null, customerId)
                .flatMap(account -> accountApi.deleteAccount(account.getAccountId())
                        .then(customerReactiveRepository.deleteById(Integer.valueOf(customerId))))
                .switchIfEmpty(customerReactiveRepository.deleteById(Integer.valueOf(customerId))
                        .then(Mono.fromRunnable(() -> log.info("<---| deleteCustomer finished successfully"))))
                .onErrorMap(error -> {
                    log.error("Account found with customer ID: {}", customerId);
                    return new CustomerException(error_005_Have_Account);
                })
                .doOnError(error -> log.error("<---| deleteCustomer - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()))
                .then();
    }

    @Override
    public Mono<Void> updateCustomer(String customerId, Customer customer) {
        log.info("|---> updateCustomer in repository");
        return customerReactiveRepository.findById(Integer.valueOf(customerId))
                .switchIfEmpty(Mono.error(new CustomerException()))
                .onErrorMap(error -> {
                    log.error("<---| updateCustomer - Error al buscar el cliente con el id: [{}] y el error es: [{}]", customerId,
                            error.getMessage());
                    return new CustomerException(error_002_Customer_Not_Fount);
                })
                .flatMap(existingCustomer -> {
                    existingCustomer.setName(customer.getName());
                    existingCustomer.setAge(customer.getAge());
                    existingCustomer.setStatus(customer.getStatus());
                    existingCustomer.setGender(customer.getGender());
                    existingCustomer.setIdentification(customer.getIdentification());
                    existingCustomer.setAddress(customer.getAddress());
                    existingCustomer.setPhone(customer.getPhone());
                    return customerReactiveRepository.save(existingCustomer);
                })
                .then();
    }

    @Override
    public Mono<Void> updatePartialCustomer(String customerId, Customer customer) {
        log.info("|---> updatePartialCustomer in repository");
        return customerReactiveRepository.findById(Integer.valueOf(customerId))
                .switchIfEmpty(Mono.error(new CustomerException()))
                .onErrorMap(error -> {
                    log.error("<---| updatePartialCustomer - Error al buscar el cliente con el id: [{}] y el error es: [{}]", customerId,
                            error.getMessage());
                    return new CustomerException(error_002_Customer_Not_Fount);
                })
                .flatMap(existingCustomer -> {
                    existingCustomer.setPassword(customer.getPassword());
                    existingCustomer.setStatus(customer.getStatus());
                    return customerReactiveRepository.save(existingCustomer);
                })
                .then();
    }

    @Override
    public Flux<Customer> findAllCustomer() {
        log.info("|---> findAllCustomer in repository");
        return customerReactiveRepository.findAll()
                .doOnError(error -> log.error("<---| findAllCustomer - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));
    }
}
