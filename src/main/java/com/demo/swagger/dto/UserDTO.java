// src/main/java/com/demo/swagger/dto/UserDTO.java
package com.demo.swagger.dto;

import com.demo.swagger.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserDTO {
    
    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please provide a valid email address")
    @Schema(example = "user@example.com", required = true, description = "Email address of the user")
    private String email;
    
    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password cannot be blank")
    @Schema(example = "yourpassword", required = true, description = "Password for the user account")
    private String password;
    
    @NotNull(message = "Role is mandatory")
    @NotBlank(message = "Role cannot be blank")
    @Pattern(regexp = "^(Creator|Promoter)$", message = "Role must be either 'Creator' or 'Promoter'")
    @Schema(
        example = "Creator", 
        required = true, 
        allowableValues = {"Creator", "Promoter"},
        description = "User role - must be either Creator or Promoter"
    )
    private String role;
    
    @NotNull(message = "Phone number is mandatory")
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Phone number must be in international format (e.g., +11234567890)")
    @Schema(
        example = "+11234567890", 
        required = true,
        description = "Phone number in international format with country code"
    )
    private String phoneNumber;
    
    // Status is optional in DTO as it defaults to ACTIVE
    @Schema(
        example = "ACTIVE",
        required = false, 
        allowableValues = {"ACTIVE", "INACTIVE", "SUSPENDED"},
        description = "User status - defaults to ACTIVE if not specified"
    )
    private UserStatus status;
    
    // Add getters and setters for the new status field
    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
    
    // Existing getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}