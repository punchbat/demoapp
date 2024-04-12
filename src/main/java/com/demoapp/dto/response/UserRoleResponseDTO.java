package com.demoapp.dto.response;

import com.demoapp.entity.UserRoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleResponseDTO {
    private String name;
    private String description;

    public UserRoleResponseDTO(UserRoleEntity userRole) {
        this.name = userRole.getName();
        this.description = userRole.getDescription();
    }
}


