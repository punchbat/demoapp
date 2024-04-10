package com.demoapp.controllers;

import com.demoapp.dto.UserDTO;
import com.demoapp.models.User;
import com.demoapp.services.UserServices;
import com.demoapp.dto.ApiDTO;
import com.demoapp.dto.UserAndTokenDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserControllers {
    private UserServices userServices = new UserServices();

    @POST
    @Path("/sign-up")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response signUp(User user) {
        UserAndTokenDTO userAndTokenDTO = userServices.signUp(user);

        if (userAndTokenDTO != null) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(ApiDTO.success("User sign-up", userAndTokenDTO))
                    .build();
        }

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiDTO.failure("User could not sign-up"))
                .build();
    }

    @Path("/sign-in")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response signIn(@FormParam("email") String email, @FormParam("password") String password) {
        UserAndTokenDTO userAndTokenDTO = userServices.signIn(email, password);

        if (userAndTokenDTO != null) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(ApiDTO.success("User sign-in", userAndTokenDTO))
                    .build();
        }

        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(ApiDTO.failure("Invalid credentials"))
                .build();
    }

    @PUT
    @Path("/profile-update/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateProfile(@PathParam("id") int id, User user) {
        UserDTO userDTO = userServices.updateProfile(id, user);

        if (userDTO != null) {
            return Response
                    .status(Response.Status.OK)
                    .entity(ApiDTO.success("User updated profile", userDTO))
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(ApiDTO.failure("User not found"))
                .build();
    }
}
