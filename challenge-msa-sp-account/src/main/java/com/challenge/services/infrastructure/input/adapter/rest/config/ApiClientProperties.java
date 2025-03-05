package com.challenge.services.infrastructure.input.adapter.rest.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "spring.http-client.api")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class ApiClientProperties {
    @NotNull
    @Valid
    private HttpApiClient clientSpTransaction;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Validated
    public static class HttpApiClient {
        @NotBlank
        @Size(max = 255)
        private String baseUrl;
    }
}