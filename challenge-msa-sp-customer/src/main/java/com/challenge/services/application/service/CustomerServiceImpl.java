package com.challenge.services.application.service;

import com.challenge.services.application.input.port.CustomerService;
import com.challenge.services.application.input.port.JwtService;
import com.challenge.services.application.output.port.RepositoryPort;
import com.challenge.services.domain.dto.Customer;
import com.challenge.services.domain.dto.Jwt;
import com.challenge.services.domain.dto.Subject;
import com.challenge.services.infrastructure.exception.CustomerException;
import com.challenge.services.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.challenge.services.infrastructure.input.adapter.rest.error.resolver.DefaultError.error_003_Unauthorized;
import static com.challenge.services.infrastructure.input.adapter.rest.error.resolver.DefaultError.error_004_Invalid_Jwt;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final RepositoryPort repositoryPort;
    private final RedisPublisherService redisPublisherService;
    private final ChannelTopic topic;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;


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
    public Flux<Customer> getCustomerByFilter(String authorization, String customerId) {
        log.info("|--> getCustomerByFilter start");
        return Mono.justOrEmpty(authorization)
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .switchIfEmpty(Mono.error(new CustomerException(error_003_Unauthorized)))
                .map(authHeader -> authHeader.substring(7))
                .flatMap(token ->  jwtService.validateToken(token, customerId)) // Assuming jwtService is a service to validate JWT
                .flatMapMany(isValid -> {
                    if (!isValid) {
                        return Flux.error(new CustomerException(error_004_Invalid_Jwt));
                    }
                    return repositoryPort.getCustomerByFilter(customerId);
                })
                .doOnNext(response -> log.info("<--| getCustomerByFilter finished successfully"))
                .doOnError(error -> log.error("<--| getCustomerByFilter finished with error {}", error.getMessage()));
    }


    @Override
    public Mono<Void> deleteCustomer(String customerId) {
        log.info("|--> deleteCustomer start");
        return repositoryPort.deleteCustomer(customerId)
                .doOnSuccess(response -> log.info("<--| deleteCustomer finished successfully"))
                .doOnError(error -> log.error("<--| deleteCustomer finished with error {}", error.getMessage()));
    }

    @Override
    public Mono<Void> putCustomer(String customerId, Customer customer) {
        log.info("|--> putCustomer start");
        return repositoryPort.putCustomer(customerId, customer)
                .doOnSuccess(response -> log.info("<--| putCustomer finished successfully"))
                .doOnError(error -> log.error("<--| putCustomer finished with error {}", error.getMessage()));
    }

    @Override
    public Mono<Void> patchCustomer(String customerId, Customer customer) {
        log.info("|--> patchCustomer start");
        return repositoryPort.patchCustomer(customerId, customer)
                .doOnSuccess(response -> log.info("<--| patchCustomer finished successfully"))
                .doOnError(error -> log.error("<--| patchCustomer finished with error {}", error.getMessage()));
    }

    @Override
    public Mono<Jwt> postCustomerGeneratedToken(Subject subject) {
        Jwt jwt = new Jwt();
        jwt.setJwt(JwtTokenUtil.generateToken(subject.getValue()));
        return Mono.just(jwt);
    }
}
