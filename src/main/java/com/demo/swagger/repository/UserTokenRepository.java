package com.demo.swagger.repository;

import com.demo.swagger.model.UserToken;
import com.demo.swagger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByUser(User user);
}