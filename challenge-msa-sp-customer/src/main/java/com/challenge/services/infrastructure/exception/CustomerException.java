package com.challenge.services.infrastructure.exception;

import com.challenge.services.infrastructure.input.adapter.rest.error.resolver.ErrorModel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@lombok.Getter
public class CustomerException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ErrorModel error;

    public CustomerException(ErrorModel error) {
        super();
        this.error = error;
    }
}
