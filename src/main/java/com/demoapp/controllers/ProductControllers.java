package com.demoapp.controllers;

import com.demoapp.dto.request.AddProductRequestDTO;
import com.demoapp.dto.request.UpdateProductRequestDTO;
import com.demoapp.dto.response.ApiResponseDTO;
import com.demoapp.dto.response.ProductResponseDTO;
import com.demoapp.entity.ProductEntity;
import com.demoapp.middleware.LoggingFilter;
import com.demoapp.service.ProductService;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/products")
public class ProductControllers {
    private ProductService productServices;

    public ProductControllers() {
        this.productServices = new ProductService();
    }

    @GET
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listProducts() {
        return Response
                .status(Response.Status.OK)
                .entity(ApiResponseDTO.success("All products", productServices.listProducts()))
                .build();
    }

    @GET
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getProduct(@PathParam("id") String id) {
        ProductResponseDTO productDTO = productServices.getProduct(id);
        return Response
                .status(Response.Status.OK)
                .entity(ApiResponseDTO.success("Get product", productDTO))
                .build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addProduct(@Context SecurityContext securityContext, @Valid AddProductRequestDTO addProductRequestDTO) {
        if (securityContext.isUserInRole("ADMIN")) {
            productServices.addProduct(addProductRequestDTO);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(ApiResponseDTO.success("Added product", null))
                    .build();
        }

        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(ApiResponseDTO.failure("Doesn't have access to resources"))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateProduct(@Context SecurityContext securityContext, @Valid UpdateProductRequestDTO updateProductRequestDTO) {
        if (securityContext.isUserInRole("ADMIN")) {
            productServices.updateProduct(updateProductRequestDTO);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(ApiResponseDTO.success("Updated product", updateProductRequestDTO))
                    .build();
        }
        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(ApiResponseDTO.failure("Doesn't have access to resources"))
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteProduct(@Context SecurityContext securityContext, @PathParam("id") String id) {
        if (securityContext.isUserInRole("ADMIN")) {
            productServices.deleteProduct(id);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(ApiResponseDTO.success("Product deleted successfully", id))
                    .build();
        }
        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(ApiResponseDTO.failure("Doesn't have access to resources"))
                .build();
    }
}
