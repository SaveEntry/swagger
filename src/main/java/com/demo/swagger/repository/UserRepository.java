package com.demo.swagger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.swagger.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAndRole(String email, String role);
}