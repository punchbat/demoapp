package com.demoapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequestDTO {
    @NotBlank(message = "Product ID is required")
    private String id;

    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @Size(max = 150, message = "Description must be less than 150 characters")
    private String description;

    private float price;
}


