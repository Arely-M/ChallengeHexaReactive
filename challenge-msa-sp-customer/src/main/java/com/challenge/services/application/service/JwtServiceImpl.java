package com.challenge.services.application.service;

import com.challenge.services.application.input.port.JwtService;
import com.challenge.services.infrastructure.exception.CustomerException;
import com.challenge.services.infrastructure.input.adapter.rest.config.PropertiesConfigurations;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Objects;

import static com.challenge.services.infrastructure.input.adapter.rest.error.resolver.DefaultError.error_003_Unauthorized;

@RequiredArgsConstructor
@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    private static PropertiesConfigurations propertiesConfigurations;

//    private static final String SECRET_KEY_BASE64 = propertiesConfigurations.getKeyDecryption();
    private static final SecretKey key = new SecretKeySpec(Base64.getDecoder().decode("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"),
            SignatureAlgorithm.HS256.getJcaName());

    @Override
    public Mono<Boolean> validateToken(String token, String id) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            if (Objects.equals(claims.getSubject(), id))// || claims.getSubject().equals(id))
                return Mono.just(true);
            else return Mono.error(new CustomerException(error_003_Unauthorized));
        } catch (Exception e) {
            return Mono.just(false);
        }
    }

    // Método opcional para generar un token
    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .signWith(key)
                .compact();
    }


}
