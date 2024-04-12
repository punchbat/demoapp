package com.demoapp.middleware;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.demoapp.dto.response.ApiResponseDTO;
import com.demoapp.service.JWTService;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getRequestUri().toString();

        if (path.contains("user/sign-up") || path.contains("user/sign-in")) {
            return;
        }

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            DecodedJWT jwt = JWTService.verifyJWT(token);
            if (jwt != null) {
                setSecurityContext(requestContext, jwt);
            } else {
                abortWithUnauthorized(requestContext);
            }
        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
    }

    private void setSecurityContext(ContainerRequestContext requestContext, DecodedJWT jwt) {
        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return () -> jwt.getSubject();
            }

            @Override
            public boolean isSecure() {
                return "https".equals(requestContext.getUriInfo().getRequestUri().getScheme());
            }

            @Override
            public boolean isUserInRole(String roleName) {
                String userRole = jwt.getClaim("roleName").asString();
                return userRole != null && userRole.equals(roleName);
            }

            @Override
            public String getAuthenticationScheme() {
                return "Bearer";
            }
        });
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity(ApiResponseDTO.failure("User cannot access the resource"))
                        .build()
        );
    }
}
