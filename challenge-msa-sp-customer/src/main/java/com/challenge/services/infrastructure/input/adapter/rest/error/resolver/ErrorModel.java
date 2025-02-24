package com.challenge.services.infrastructure.input.adapter.rest.error.resolver;

import lombok.AllArgsConstructor;

@lombok.Getter
@lombok.Setter
@AllArgsConstructor
public class ErrorModel {
    private String code;
    private String message;

}