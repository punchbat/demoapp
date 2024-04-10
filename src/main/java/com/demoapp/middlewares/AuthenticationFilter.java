package com.demoapp.middlewares;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.demoapp.dto.ApiDTO;
import com.demoapp.services.JWTService;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
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
            public boolean isUserInRole(String role) {
                String userRole = jwt.getClaim("role").asString();
                return userRole != null && userRole.equals(role);
            }

            @Override
            public boolean isSecure() {
                return "https".equals(requestContext.getUriInfo().getRequestUri().getScheme());
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
                        .entity(ApiDTO.failure("User cannot access the resource"))
                        .build()
        );
    }
}
