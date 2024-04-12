package com.demoapp.middleware;

import jakarta.ws.rs.container.ContainerRequestContext;
import org.apache.logging.log4j.LogManager;

import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Provider
public class LoggingFilter implements ContainerRequestFilter {
    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String method = containerRequestContext.getMethod();
        String path = containerRequestContext.getUriInfo().getRequestUri().toString();
        String user = containerRequestContext.getSecurityContext().getUserPrincipal() != null ?
                containerRequestContext.getSecurityContext().getUserPrincipal().getName() : "anonymous";

        logger.info("Request " + method + " " + path + " from user " + user);
    }
}