package com.challenge.services.infrastructure.input.adapter.rest.config;

import com.challenge.services.input.clientSpTransaction.TransactionApi;
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
    public TransactionApi transactionApi() {
        final var clientApi = new TransactionApi();
        clientApi.getApiClient().setBasePath(apiClientProperties.getClientSpTransaction().getBaseUrl());
        return clientApi;
    }
}
