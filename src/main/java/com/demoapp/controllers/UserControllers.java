package com.demoapp.controllers;

import com.demoapp.dto.request.SignInRequestDTO;
import com.demoapp.dto.request.SignUpRequestDTO;
import com.demoapp.dto.request.UpdateProfileRequestDTO;
import com.demoapp.dto.response.UserResponseDTO;
import com.demoapp.service.UserService;
import com.demoapp.dto.response.ApiResponseDTO;
import com.demoapp.dto.response.UserAndTokenResponseDTO;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
public class UserControllers {
    private UserService userServices;

    public UserControllers() {
        this.userServices = new UserService();
    }

    @POST
    @Path("/sign-up")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response signUp(@Valid SignUpRequestDTO signUpRequestDTO) {
        UserAndTokenResponseDTO userAndTokenDTO = userServices.signUp(signUpRequestDTO);
        return Response
                .status(Response.Status.CREATED)
                .entity(ApiResponseDTO.success("User sign-up", userAndTokenDTO))
                .build();
    }

    @Path("/sign-in")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response signIn(@Valid SignInRequestDTO signInRequestDTO) {
        UserAndTokenResponseDTO userAndTokenDTO = userServices.signIn(signInRequestDTO);
        return Response
                .status(Response.Status.CREATED)
                .entity(ApiResponseDTO.success("User sign-in", userAndTokenDTO))
                .build();
    }

    @PUT
    @Path("/profile-update/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateProfile(@Valid UpdateProfileRequestDTO updateProfileRequestDTO) {
        UserResponseDTO userDTO = userServices.updateProfile(updateProfileRequestDTO);
        return Response
                .status(Response.Status.OK)
                .entity(ApiResponseDTO.success("User updated profile", userDTO))
                .build();
    }
}
