package com.demoapp.exception;

import com.demoapp.exception.mapper.ClientException;

public class SignInException extends ClientException {
    public SignInException(int statusCode, Error error, String message) {
        super(statusCode, error, message);
    }
}
