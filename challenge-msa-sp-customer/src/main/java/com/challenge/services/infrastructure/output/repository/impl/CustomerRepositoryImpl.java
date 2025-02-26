package com.challenge.services.infrastructure.output.repository.impl;

import com.challenge.services.infrastructure.exception.CustomerException;
import com.challenge.services.infrastructure.output.repository.CustomerReactiveRepository;
import com.challenge.services.infrastructure.output.repository.CustomerRepository;
import com.challenge.services.infrastructure.output.repository.entity.CustomerEntity;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.challenge.services.infrastructure.input.adapter.rest.error.resolver.DefaultError.error_002_Customer_Not_Fount;

@Repository
@RequiredArgsConstructor
@Slf4j
@Generated
public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerReactiveRepository customerReactiveRepository;

    @Override
    public Mono<CustomerEntity> saveCustomer(CustomerEntity customerEntity) {
        log.info("|---> saveCustomer in repository");
        return customerReactiveRepository.save(customerEntity);
    }

    @Override
    public Mono<CustomerEntity> findByCustomerId(String customerId) {
        log.info("|---> findByCustomerId in repository");
        return Optional.ofNullable(customerId)
                .map(s -> customerReactiveRepository.findById(Integer.valueOf(s)))
                .orElse(Mono.error(new CustomerException()))
                .doOnError(error -> log.error("<---| findByCustomerId - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));

    }

    @Override
    public Mono<Void> deleteCustomer(String customerId) {
        log.info("|---> deleteCustomer in repository");
        return customerReactiveRepository.deleteById(Integer.valueOf(customerId))
                .doOnError(error -> log.error("<---| deleteCustomer - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));

    }

    @Override
    public Mono<Void> updateCustomer(String customerId, CustomerEntity customerEntity) {
        log.info("|---> updateCustomer in repository");
        return customerReactiveRepository.findById(Integer.valueOf(customerId))
                .switchIfEmpty(Mono.error(new CustomerException()))
                .onErrorMap(error -> {
                    log.error("<---| updateCustomer - Error al buscar el cliente con el id: [{}] y el error es: [{}]", customerId,
                            error.getMessage());
                    return new CustomerException(error_002_Customer_Not_Fount);
                })
                .flatMap(existingCustomer -> {
                    existingCustomer.setName(customerEntity.getName());
                    existingCustomer.setAge(customerEntity.getAge());
                    existingCustomer.setStatus(customerEntity.getStatus());
                    existingCustomer.setGender(customerEntity.getGender());
                    existingCustomer.setIdentification(customerEntity.getIdentification());
                    existingCustomer.setAddress(customerEntity.getAddress());
                    existingCustomer.setPhone(customerEntity.getPhone());
                    return customerReactiveRepository.save(existingCustomer);
                })
                .then();
    }

    @Override
    public Mono<Void> updatePartialCustomer(String customerId, CustomerEntity customerEntity) {
        log.info("|---> updatePartialCustomer in repository");
        return customerReactiveRepository.findById(Integer.valueOf(customerId))
                .switchIfEmpty(Mono.error(new CustomerException()))
                .onErrorMap(error -> {
                    log.error("<---| updatePartialCustomer - Error al buscar el cliente con el id: [{}] y el error es: [{}]", customerId,
                            error.getMessage());
                    return new CustomerException(error_002_Customer_Not_Fount);
                })
                .flatMap(existingCustomer -> {
                    existingCustomer.setPassword(customerEntity.getPassword());
                    existingCustomer.setStatus(customerEntity.getStatus());
                    return customerReactiveRepository.save(existingCustomer);
                })
                .then();
    }

    @Override
    public Flux<CustomerEntity> findAllCustomer() {
        log.info("|---> findAllCustomer in repository");
        return customerReactiveRepository.findAll()
                .doOnError(error -> log.error("<---| findAllCustomer - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));
    }
}
