package com.challenge.services.application.service;

import com.challenge.services.application.input.port.CustomerService;
import com.challenge.services.application.output.port.CustomerRepository;
import com.challenge.services.infrastructure.input.adapter.rest.mapper.CustomerMapper;
import com.challenge.services.input.server.models.Customer;
import com.challenge.services.input.server.models.PostCustomerRequest;
import com.challenge.services.input.server.models.PutCustomerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Mono<Void> createCustomer(Mono<PostCustomerRequest> postCustomerRequest) {
        log.info("|--> createCustomer started");
        return postCustomerRequest
                .flatMap(request -> customerRepository.save(CustomerMapper.INSTANCE.mapperToCustomer(request)))
                .doOnSuccess(response -> log.info("<--| createCustomer finished successfully"))
                .doOnError(error -> log.error("<--| createCustomer finished with error", error))
                .then();
    }

    @Override
    public Flux<Customer> getCustomerByFilter(String customerId) {
        return customerRepository.findById(customerId)
                .flux();
    }

    @Override
    public Mono<Void> deleteCustomer(String customerId) {
        return customerRepository.deleteById(customerId);
    }

    @Override
    public Mono<Void> putCustomer(String customerId, Mono<PutCustomerRequest> putCustomerRequest) {
        return putCustomerRequest
                .flatMap(request -> customerRepository.findById(customerId))
                .map(customerRepository::save)
                .then();
    }
}
