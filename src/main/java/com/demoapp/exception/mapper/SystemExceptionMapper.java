package com.demoapp.exception.mapper;


import com.demoapp.dto.response.ApiErrorResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SystemExceptionMapper implements ExceptionMapper<SystemException> {
    @Override
    public Response toResponse(SystemException exception) {
        // 5xx errors
        SystemException systemException = (SystemException) exception;
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                systemException.getStatusCode(),
                systemException.getError(),
                systemException.getMessage()
        );
        return Response
                .status(systemException.getStatusCode())
                .entity(errorResponse)
                .build();
    }
}
