package com.demo.swagger.repository;

import com.demo.swagger.model.UserToken;
import com.demo.swagger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

//In UserTokenRepository.java, add this method
//In UserTokenRepository.java
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
 Optional<UserToken> findByUser(User user);
 void deleteByUser(User user);
}

