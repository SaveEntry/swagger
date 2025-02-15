package com.demo.swagger.dto;

import jakarta.validation.constraints.NotNull;
import com.demo.swagger.enums.UserRole;

public class PrivilegeUpdateDTO {
    @NotNull(message = "User role is mandatory")
    private UserRole userRole;

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}