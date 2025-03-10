package com.challenge.services.infrastructure.input.adapter.rest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@lombok.Getter
@lombok.Setter
@Configuration
@ConfigurationProperties
public class PropertiesConfigurations {
    @Value("${key}")
    private String keyDecryption;
}
