package com.challenge.services.infrastructure.output.adapter;

import com.challenge.services.application.output.port.RepositoryPort;
import com.challenge.services.infrastructure.output.adapter.mapper.PostgreSQLRepositoryAdapterMapper;
import com.challenge.services.infrastructure.output.repository.CustomerRepository;
import com.challenge.services.input.server.models.Customer;
import com.challenge.services.input.server.models.PatchCustomerRequest;
import com.challenge.services.input.server.models.PostCustomerRequest;
import com.challenge.services.input.server.models.PutCustomerRequest;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostgresRepositoryAdapter implements RepositoryPort {
    private final CustomerRepository customerRepository;

    @Override
    public Mono<com.challenge.services.domain.Customer> createCustomer(PostCustomerRequest postCustomerRequest) {
        return customerRepository.saveCustomer(PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperToCustomerEntity(postCustomerRequest))
                .map(PostgreSQLRepositoryAdapterMapper.INSTANCE::mapperToCustomerDomain)
                .doOnSuccess(response -> log.info("<---| createCustomer finished successfully"))
                .doOnError(error -> log.error("<---| createCustomer - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));
    }

    @Override
    public Flux<Customer> getCustomerByFilter(String customerId) {
        if (customerId == null) {
            return getCustomers();
        }
        return getCustomerById(customerId);
    }

    private Flux<Customer> getCustomers() {
        return customerRepository.findAllCustomer()
                .map(customerEntity -> {
                    log.info("customers: {}", new Gson().toJson(customerEntity));
                    return customerEntity;
                })
                .map(PostgreSQLRepositoryAdapterMapper.INSTANCE::mapperToCustomer);
    }

    private Flux<Customer> getCustomerById(String customerId) {
        return customerRepository.findByCustomerId(customerId)
                .map(customerEntity -> {
                    log.info("customer: {}", new Gson().toJson(customerEntity));
                    return customerEntity;
                })
                .map(PostgreSQLRepositoryAdapterMapper.INSTANCE::mapperToCustomer)
                .flux();
    }

    @Override
    public Mono<Void> deleteCustomer(String customerId) {
        return customerRepository.deleteCustomer(customerId);
    }

    @Override
    public Mono<Void> putCustomer(String customerId, PutCustomerRequest putCustomerRequest) {
        return customerRepository.updateCustomer(
                        customerId,
                        PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperPutCustomerToCustomerEntity(putCustomerRequest))
                .doOnSuccess(response -> log.info("<---| putCustomer finished successfully"))
                .doOnError(error -> log.error("<---| putCustomer - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));
    }

    @Override
    public Mono<Void> patchCustomer(String customerId, PatchCustomerRequest patchCustomerRequest) {
        return customerRepository
                .updatePartialCustomer(
                        customerId,
                        PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperPatchCustomerToCustomerEntity(patchCustomerRequest))
                .doOnSuccess(response -> log.info("<---| patchCustomer finished successfully"))
                .doOnError(error -> log.error("<---| patchCustomer - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));
    }
}
