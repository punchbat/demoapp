package com.demoapp.exception;

import com.demoapp.exception.mapper.ClientException;

public class DatabaseException extends ClientException {
    public DatabaseException(int statusCode, Error error, String message) {
        super(statusCode, error, message);
    }
}
