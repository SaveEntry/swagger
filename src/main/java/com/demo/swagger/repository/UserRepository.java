package com.demo.swagger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    
    @Modifying
    @Query(value = "DELETE FROM users WHERE id = :id", nativeQuery = true)
    void deleteUserById(@Param("id") Long id);
    
    boolean existsById(Long id);
     
}