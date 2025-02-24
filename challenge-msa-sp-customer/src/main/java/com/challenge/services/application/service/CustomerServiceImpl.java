package com.challenge.services.application.service;

import com.challenge.services.application.input.port.CustomerService;
import com.challenge.services.application.output.port.RepositoryPort;
import com.challenge.services.input.server.models.Customer;
import com.challenge.services.input.server.models.PatchCustomerRequest;
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
    private final RepositoryPort repositoryPort;

    @Override
    public Mono<Void> createCustomer(Mono<PostCustomerRequest> postCustomerRequestMono) {
        log.info("|--> createCustomer start");
        return postCustomerRequestMono
                .flatMap(repositoryPort::createCustomer)
                .doOnSuccess(response -> log.info("<--| createCustomer finished successfully"))
                .doOnError(error -> log.error("<--| createCustomer finished with error", error))
                .then();
    }

    @Override
    public Flux<Customer> getCustomerByFilter(String customerId) {
        log.info("|--> getCustomerByFilter start");
        return repositoryPort.getCustomerByFilter(customerId)
                .doOnNext(response -> log.info("<--| getCustomerByFilter finished successfully"))
                .doOnError(error -> log.error("<--| getCustomerByFilter finished with error", error));
    }

    @Override
    public Mono<Void> deleteCustomer(String customerId) {
        log.info("|--> deleteCustomer start");
        return repositoryPort.deleteCustomer(customerId)
                .doOnSuccess(response -> log.info("<--| deleteCustomer finished successfully"))
                .doOnError(error -> log.error("<--| deleteCustomer finished with error", error));
    }

    @Override
    public Mono<Void> putCustomer(String customerId, Mono<PutCustomerRequest> putCustomerRequestMono) {
        log.info("|--> putCustomer start");
        return putCustomerRequestMono
                .flatMap(putCustomerRequest -> repositoryPort.putCustomer(customerId, putCustomerRequest))
                .doOnSuccess(response -> log.info("<--| putCustomer finished successfully"))
                .doOnError(error -> log.error("<--| putCustomer finished with error", error));
    }

    @Override
    public Mono<Void> patchCustomer(String customerId, Mono<PatchCustomerRequest> patchCustomerRequestMono) {
        log.info("|--> patchCustomer start");
        return patchCustomerRequestMono
                .flatMap(patchCustomerRequest -> repositoryPort.patchCustomer(customerId, patchCustomerRequest))
                .doOnSuccess(response -> log.info("<--| patchCustomer finished successfully"))
                .doOnError(error -> log.error("<--| patchCustomer finished with error", error));
    }
}
