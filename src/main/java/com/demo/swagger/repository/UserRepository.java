package com.demo.swagger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.swagger.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAndRole(String email, String role);
    Optional<User> findByEmailAndRoleAndPasswordAndPhoneNumber(
        String email, 
        String role, 
        String password, 
        String phoneNumber
    );
}