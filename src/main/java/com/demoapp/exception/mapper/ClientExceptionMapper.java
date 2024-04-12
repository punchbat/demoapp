package com.demoapp.exception.mapper;


import com.demoapp.dto.response.ApiErrorResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ClientExceptionMapper implements ExceptionMapper<ClientException> {
    @Override
    public Response toResponse(ClientException exception) {
        // 4xx errors
        ClientException clientException = (ClientException) exception;
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                clientException.getStatusCode(),
                clientException.getError(),
                clientException.getMessage()
        );
        return Response
                .status(clientException.getStatusCode())
                .entity(errorResponse)
                .build();

    }
}
