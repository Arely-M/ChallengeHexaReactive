package com.challenge.services.application.service;

import com.challenge.services.application.input.port.CustomerService;
import com.challenge.services.application.output.port.RepositoryPort;
import com.challenge.services.domain.dto.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final RepositoryPort repositoryPort;
    private final RedisPublisherService redisPublisherService;
    private final ChannelTopic topic;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> createCustomer(com.challenge.services.domain.dto.Customer customer) {
        log.info("|--> createCustomer start");
        return repositoryPort.createCustomer(customer)
                .flatMap(this::sendEvent)
                .doOnSuccess(response -> log.info("<--| createCustomer finished successfully"))
                .doOnError(error -> log.error("<--| createCustomer finished with error", error));
    }

    private Mono<Void> sendEvent(com.challenge.services.domain.dto.Customer customerDto) {
        return Mono.fromCallable(() -> new Gson().toJson(customerDto))
                .flatMap(jsonData -> {
                    log.info("Starting to send the event with data: {}", jsonData);
                    return redisPublisherService.publishMessage(topic.getTopic(), jsonData);
                    //return publisherEventService.sendCreateMatrixEvent(jsonData);
                });
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
    public Mono<Void> putCustomer(String customerId, Customer customer) {
        log.info("|--> putCustomer start");
        return repositoryPort.putCustomer(customerId, customer)
                .doOnSuccess(response -> log.info("<--| putCustomer finished successfully"))
                .doOnError(error -> log.error("<--| putCustomer finished with error", error));
    }

    @Override
    public Mono<Void> patchCustomer(String customerId, Customer customer) {
        log.info("|--> patchCustomer start");
        return repositoryPort.patchCustomer(customerId, customer)
                .doOnSuccess(response -> log.info("<--| patchCustomer finished successfully"))
                .doOnError(error -> log.error("<--| patchCustomer finished with error", error));
    }
}
