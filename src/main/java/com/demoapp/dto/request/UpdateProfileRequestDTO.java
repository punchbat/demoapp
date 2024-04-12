package com.demoapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequestDTO {
    @NotBlank(message = "User ID is required")
    private String id;

    private String roleID;

    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @Size(max = 100, message = "Surname must be less than 100 characters")
    private String surname;

    @Min(value = 1, message = "Age must be greater than 0")
    @Min(value = 110, message = "Age must be less than 110")
    private int age;
}


