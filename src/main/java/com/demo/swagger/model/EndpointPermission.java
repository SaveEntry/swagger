// src/main/java/com/demo/swagger/model/EndpointPermission.java
package com.demo.swagger.model;

import jakarta.persistence.*;
import com.demo.swagger.enums.UserRole;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "endpoint_permissions")
public class EndpointPermission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String path;
    
    @Column(nullable = false)
    private String method;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "endpoint_roles",
        joinColumns = @JoinColumn(name = "endpoint_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<UserRole> allowedRoles = new HashSet<>();
    
    @Column(length = 255)
    private String description;
    
    @Column(length = 100)
    private String controllerName;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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