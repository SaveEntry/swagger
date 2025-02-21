package com.demo.swagger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.swagger.model.Privilege;
import com.demo.swagger.model.User;
import java.util.Optional;

//In PrivilegeRepository.java
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
Optional<Privilege> findByUser(User user);
void deleteByUser(User user);
void deleteAllByUser(User user);


@Modifying
@Query(value = "DELETE FROM privileges WHERE user_id = :userId", nativeQuery = true)
void deleteByUserId(@Param("userId") Long userId);
}