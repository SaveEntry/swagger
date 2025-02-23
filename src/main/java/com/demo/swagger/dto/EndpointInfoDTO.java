package com.demo.swagger.dto;

import java.util.Set;
import com.demo.swagger.enums.UserRole;

public class EndpointInfoDTO {
    private String path;
    private String method;
    private Set<UserRole> roles;
    private String description;
    private String controllerName;

    public EndpointInfoDTO(String path, String method, Set<UserRole> roles, String description, String controllerName) {
        this.path = path;
        this.method = method;
        this.roles = roles;
        this.description = description;
        this.controllerName = controllerName;
    }

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

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }
}