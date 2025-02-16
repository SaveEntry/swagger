package com.demo.swagger.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SignInDTO {
    
    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please provide a valid email address")
    private String email;
    
    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password cannot be blank")
    private String password;
    
    @NotNull(message = "Role is mandatory")
    @NotBlank(message = "Role cannot be blank")
    private String role;
    
    @NotNull(message = "Phone number is mandatory")
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;
    
    // Getters and Setters
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