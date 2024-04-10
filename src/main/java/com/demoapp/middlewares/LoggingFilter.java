package com.demoapp.middlewares;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Provider
public class LoggingFilter implements ContainerRequestFilter {
    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getRequestUri().toString();
        String user = requestContext.getSecurityContext().getUserPrincipal() != null ?
                requestContext.getSecurityContext().getUserPrincipal().getName() : "anonymous";

        LOGGER.info("Request " + method + " " + path + " from user " + user);
    }
}