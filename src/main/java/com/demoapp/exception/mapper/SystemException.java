package com.demoapp.exception.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SystemException extends RuntimeException {
    private final int statusCode;
    private final Error error;

    public SystemException(int statusCode, Error error, String message) {
        super(message);
        this.statusCode = statusCode;
        this.error = error;
    }
}

