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
import jakarta.persistence.EntityManager;
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
    
    @Autowired
    private EntityManager entityManager; // Add this

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
            // First check if user exists
            if (!userRepository.existsById(id)) {
                throw new EntityNotFoundException("User not found with id: " + id);
            }

            // Execute delete queries in correct order
            entityManager.createNativeQuery("DELETE FROM user_tokens WHERE user_id = :userId")
                .setParameter("userId", id)
                .executeUpdate();

            entityManager.createNativeQuery("DELETE FROM privileges WHERE user_id = :userId")
                .setParameter("userId", id)
                .executeUpdate();

            entityManager.createNativeQuery("DELETE FROM users WHERE id = :userId")
                .setParameter("userId", id)
                .executeUpdate();

            entityManager.flush();
            
        } catch (EntityNotFoundException e) {
            throw e;
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