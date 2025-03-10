package com.challenge.services.application.input.port;

import reactor.core.publisher.Mono;

public interface JwtService {
    Mono<Boolean> validateToken(String token, String id);
}
