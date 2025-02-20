// src/main/java/com/demo/swagger/service/UserService.java
package com.demo.swagger.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.swagger.dto.UserDTO;
import com.demo.swagger.model.User;
import com.demo.swagger.repository.UserRepository;
import com.demo.swagger.repository.UserTokenRepository;
import com.demo.swagger.exception.UserAlreadyExistsException;
import jakarta.transaction.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PrivilegeService privilegeService;
    
 // In UserService.java, add these repository dependencies
    @Autowired
    private UserTokenRepository userTokenRepository;

    @Transactional
    public User createUser(UserDTO userDTO) {
        if (userRepository.existsByEmailAndRole(userDTO.getEmail(), userDTO.getRole())) {
            throw new UserAlreadyExistsException(
                String.format("User already exists with email %s and role %s", 
                userDTO.getEmail(), userDTO.getRole())
            );
        }
        
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        
        // Set status if provided in DTO, otherwise it defaults to ACTIVE in the entity
        if (userDTO.getStatus() != null) {
            user.setStatus(userDTO.getStatus());
        }
        
        user = userRepository.save(user);
        
        // Create initial privilege
        privilegeService.createInitialPrivilege(user);
        
        return user;
    }
    
    @Transactional
    public void deleteUser(Long id) {
        try {
            User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

            // Clear relationships
            if (user.getPrivileges() != null) {
                user.getPrivileges().clear();
            }
            
            // Delete user token if exists
            userTokenRepository.findByUser(user)
                .ifPresent(userTokenRepository::delete);

            // Finally delete the user
            userRepository.delete(user);
            
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage());
        }
    }
    
    // Other methods remain the same
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}