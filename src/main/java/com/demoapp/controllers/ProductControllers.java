package com.demoapp.controllers;

import com.demoapp.dto.ApiDTO;
import com.demoapp.dto.ProductDTO;
import com.demoapp.models.Product;
import com.demoapp.services.ProductServices;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/products")
public class ProductControllers {

    private ProductServices productServices = new ProductServices();

    @GET
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listProducts() {
        return Response
                .status(Response.Status.OK)
                .entity(ApiDTO.success("All products", productServices.listProducts()))
                .build();
    }

    @GET
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getProduct(@PathParam("id") int id) {
        ProductDTO productDTO = productServices.getProduct(id);

        if (productDTO != null) {
            return Response
                    .status(Response.Status.OK)
                    .entity(ApiDTO.success("Get product", productDTO))
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(ApiDTO.failure("Product not found"))
                .build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addProduct(@Context SecurityContext securityContext, Product product) {
        if (securityContext.isUserInRole("ADMIN")) {
            ProductDTO productDTO = productServices.addProduct(product);

            if (productDTO != null) {
                return Response
                        .status(Response.Status.CREATED)
                        .entity(ApiDTO.success("Added product", productDTO))
                        .build();
            }

            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(ApiDTO.failure("Product not added"))
                    .build();
        }

        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(ApiDTO.failure("Doesn't have access to resources"))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateProduct(@Context SecurityContext securityContext, @PathParam("id") int id, Product product) {
        if (securityContext.isUserInRole("ADMIN")) {
            product.setId(id);
            ProductDTO productDTO = productServices.updateProduct(id, product);

            if (productDTO != null) {
                return Response
                        .status(Response.Status.CREATED)
                        .entity(ApiDTO.success("Updated product", productDTO))
                        .build();
            }

            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(ApiDTO.failure("Product not updated"))
                    .build();

        }

        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(ApiDTO.failure("Doesn't have access to resources"))
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteProduct(@Context SecurityContext securityContext, @PathParam("id") int id) {
        if (securityContext.isUserInRole("ADMIN")) {
            ProductDTO productDTO = productServices.deleteProduct(id);

            if (productDTO != null) {
                return Response
                        .status(Response.Status.CREATED)
                        .entity(ApiDTO.success("Product deleted successfully", id))
                        .build();
            }

            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(ApiDTO.failure("Product not deleted"))
                    .build();
        }

        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(ApiDTO.failure("Doesn't have access to resources"))
                .build();
    }
}
