package com.demoapp.exception;

import com.demoapp.exception.mapper.ClientException;

public class ServiceException extends ClientException {
    public ServiceException(int statusCode, Error error, String message) {
        super(statusCode, error, message);
    }
}
