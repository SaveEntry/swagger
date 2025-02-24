// src/main/java/com/demo/swagger/dto/EndpointPermissionUpdateDTO.java
package com.demo.swagger.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import com.demo.swagger.enums.UserRole;
import java.util.Set;

public class EndpointPermissionUpdateDTO {
    
    @NotNull(message = "Path is mandatory")
    private String path;
    
    @NotNull(message = "Method is mandatory")
    private String method;
    
    @NotEmpty(message = "At least one role must be specified")
    private Set<UserRole> allowedRoles;
    
    // Getters and Setters
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public Set<UserRole> getAllowedRoles() {
        return allowedRoles;
    }
    
    public void setAllowedRoles(Set<UserRole> allowedRoles) {
        this.allowedRoles = allowedRoles;
    }
}