package com.challenge.services.infrastructure.input.adapter.rest.impl;

import com.challenge.services.application.input.port.CustomerService;
import com.challenge.services.input.server.SupportApi;
import com.challenge.services.input.server.models.Customer;
import com.challenge.services.input.server.models.PatchCustomerRequest;
import com.challenge.services.input.server.models.PostCustomerRequest;
import com.challenge.services.input.server.models.PutCustomerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerApiController implements SupportApi {
    private final CustomerService customerService;

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(String customerId, ServerWebExchange exchange) {
        log.info("|-> SP deleteCustomer started");
        return customerService.deleteCustomer(customerId)
                .doOnSuccess(response -> log.info("<-| SP deleteCustomer finished successfully"))
                .doOnError(error -> log.error("<-| SP deleteCustomer finished with error", error))
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<Customer>>> getCustomerByFilter(String customerId, ServerWebExchange exchange) {
        log.info("|-> SP getCustomerByFilter started");
        return customerService.getCustomerByFilter(customerId)
                .collectList()
                .map(customers -> ResponseEntity.ok(Flux.fromIterable(customers)))
                .doOnSuccess(response -> log.info("<-| SP getCustomerByFilter finished successfully"))
                .doOnError(error -> log.error("<-| SP getCustomerByFilter finished with error", error));
    }

    @Override
    public Mono<ResponseEntity<Void>> patchCustomer(String customerId, Mono<PatchCustomerRequest> patchCustomerRequest, ServerWebExchange exchange) {
        log.info("|-> SP patchCustomer started");
        return customerService.patchCustomer(customerId, patchCustomerRequest)
                .doOnSuccess(response -> log.info("<-| SP patchCustomer finished successfully"))
                .doOnError(error -> log.error("<-| SP patchCustomer finished with error", error))
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> postCustomer(Mono<PostCustomerRequest> postCustomerRequest, ServerWebExchange exchange) {
        log.info("|-> SP postCustomer started");
        return customerService.createCustomer(postCustomerRequest)
                .doOnSuccess(response -> log.info("<-| SP postCustomer finished successfully"))
                .doOnError(error -> log.error("<-| SP postCustomer finished with error", error))
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> putCustomer(String customerId, Mono<PutCustomerRequest> putCustomerRequest, ServerWebExchange exchange) {
        log.info("|-> SP putCustomer started");
        return customerService.putCustomer(customerId, putCustomerRequest)
                .doOnSuccess(response -> log.info("<-| SP putCustomer finished successfully"))
                .doOnError(error -> log.error("<-| SP putCustomer finished with error", error))
                .then(Mono.just(ResponseEntity.ok().build()));
    }
}
