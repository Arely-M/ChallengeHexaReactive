package com.challenge.services.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisherService {
    private final RedisTemplate<String, Object> redisTemplate;

    public Mono<Void> publishMessage(String channel, String message) {
        return Mono.just(redisTemplate.convertAndSend(channel, message))
                .then()
                .doOnError(err -> log.error("Error sending data to topic: {}", err.getMessage()))
                .doOnSuccess(send -> log.info("Successfully sent matrix data to topic"));
    }
}
