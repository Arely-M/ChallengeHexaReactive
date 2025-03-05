package com.challenge.services.infrastructure.input.adapter.rest.config;

import com.challenge.services.input.clientSpAccount.AccountApi;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ApiClientConfiguration {
    private final ApiClientProperties apiClientProperties;

    @Bean
    @NonNull
    public AccountApi accountApi() {
        final var clientApi = new AccountApi();
        clientApi.getApiClient()
                .setBasePath(apiClientProperties.getClientSpAccount().getBaseUrl());
        return clientApi;
    }
}
