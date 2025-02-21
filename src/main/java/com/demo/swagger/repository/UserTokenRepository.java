package com.demo.swagger.repository;

import com.demo.swagger.model.UserToken;

import jakarta.transaction.Transactional;

import com.demo.swagger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByUser(User user);
    
    
    @Query("SELECT t FROM UserToken t JOIN FETCH t.user")
    List<UserToken> findAll();
    
    @Transactional
    void deleteByUser(User user);
    
    @Modifying
    @Query(value = "DELETE FROM user_tokens WHERE user_id = :userId", nativeQuery = true)
    void deleteByUserId(@Param("userId") Long userId);
}