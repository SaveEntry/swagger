package com.demo.swagger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.swagger.model.Privilege;
import com.demo.swagger.model.User;
import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Optional<Privilege> findByUser(User user);
}