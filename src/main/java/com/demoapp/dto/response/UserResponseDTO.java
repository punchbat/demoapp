package com.demoapp.dto.response;

import com.demoapp.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String id;
    private UserRoleResponseDTO role;
    private String name;
    private String surname;
    private int age;
    private String email;

    public UserResponseDTO(UserEntity user) {
        this.id = user.getId().toString();
        this.role = new UserRoleResponseDTO(user.getRole());
        this.name = user.getName();
        this.surname = user.getSurname();
        this.age = user.getAge();
        this.email = user.getEmail();
    }
}


