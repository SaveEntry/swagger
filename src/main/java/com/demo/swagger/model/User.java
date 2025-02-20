// src/main/java/com/demo/swagger/model/User.java
package com.demo.swagger.model;

import com.demo.swagger.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "role"})
    }
)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please provide a valid email address")
    @Column(nullable = false)
    private String email;
    
    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password cannot be blank")
    @Column(nullable = false)
    private String password;
    
    @NotNull(message = "Role is mandatory")
    @NotBlank(message = "Role cannot be blank")
    @Pattern(regexp = "^(Creator|Promoter)$", message = "Role must be either 'Creator' or 'Promoter'")
    @Column(nullable = false)
    private String role;
    
    @NotNull(message = "Phone number is mandatory")
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Phone number must be in international format (e.g., +11234567890)")
    @Column(nullable = false)
    private String phoneNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'ACTIVE'")
    private UserStatus status = UserStatus.ACTIVE;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private Set<Privilege> privileges = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private UserToken userToken;

    // Add getters and setters for the new status field
    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    // Existing getters and setters
    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}