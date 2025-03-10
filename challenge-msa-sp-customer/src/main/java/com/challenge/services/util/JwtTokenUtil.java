package com.challenge.services.util;

import com.challenge.services.infrastructure.input.adapter.rest.config.PropertiesConfigurations;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
public class JwtTokenUtil {
//    private static PropertiesConfigurations propertiesConfigurations;

    @Getter
//    private static final String SECRET_KEY_BASE64 = propertiesConfigurations.getKeyDecryption();

    private static final SecretKey key = new SecretKeySpec(Base64.getDecoder().decode("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"),
            SignatureAlgorithm.HS256.getJcaName());

    public static String generateToken(String subject) {
        // expiration (e.g., 10 minutes)
        long expirationTime = 1000 * 60 * 10;

        return Jwts.builder()
                .setSubject(subject) // Set the subject (usually the user identifier)
                .setIssuedAt(new Date()) // Set the issue time
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }
}
