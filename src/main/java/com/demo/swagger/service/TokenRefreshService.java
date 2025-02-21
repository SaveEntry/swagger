package com.demo.swagger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.demo.swagger.model.UserToken;
import com.demo.swagger.repository.UserTokenRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class TokenRefreshService {

    @Autowired
    private UserTokenRepository userTokenRepository;
    
    @Autowired
    private JwtService jwtService;
    
    @Scheduled(fixedRate = 60000) // Run every minute
    @Transactional // Add this annotation
    public void refreshTokens() {
        List<UserToken> tokens = userTokenRepository.findAll();
        
        for (UserToken token : tokens) {
            if (token.getUser() != null) {  // Add null check
                String newToken = jwtService.generateToken(
                    token.getUser().getEmail(), 
                    token.getUser().getRole()
                );
                
                token.setToken(newToken);
                token.setExpiryTime(LocalDateTime.now().plusMinutes(1));
                userTokenRepository.save(token);
            }
        }
    }
}