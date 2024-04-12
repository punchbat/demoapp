package com.demoapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {
    @NotBlank(message = "Role ID is required")
    private String roleID;

    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @Size(max = 100, message = "Surname must be less than 100 characters")
    private String surname;

    @Min(value = 1, message = "Age must be greater than 0")
    @Min(value = 110, message = "Age must be less than 110")
    private int age;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}


