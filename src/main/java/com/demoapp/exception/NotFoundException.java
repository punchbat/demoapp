package com.demoapp.exception;

import com.demoapp.exception.mapper.ClientException;

public class NotFoundException extends ClientException {
    public NotFoundException(int statusCode, Error error, String message) {
        super(statusCode, error, message);
    }
}