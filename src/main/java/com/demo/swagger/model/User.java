package com.demo.swagger.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Privilege> privileges;

    // Existing getters and setters remain the same
    
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