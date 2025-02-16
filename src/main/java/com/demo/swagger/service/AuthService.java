package com.demo.swagger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.swagger.dto.SignInDTO;
import com.demo.swagger.model.User;
import com.demo.swagger.model.UserToken;
import com.demo.swagger.repository.UserRepository;
import com.demo.swagger.repository.UserTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserTokenRepository userTokenRepository;
    
    @Autowired
    private JwtService jwtService;
    
    public String signIn(SignInDTO signInDTO) {
        User user = userRepository.findByEmailAndRoleAndPasswordAndPhoneNumber(
            signInDTO.getEmail(),
            signInDTO.getRole(),
            signInDTO.getPassword(),
            signInDTO.getPhoneNumber()
        ).orElseThrow(() -> new EntityNotFoundException("Invalid credentials"));
        
        String token = jwtService.generateToken(user.getEmail(), user.getRole());
        
        // Save or update token
        UserToken userToken = userTokenRepository.findByUser(user)
            .orElse(new UserToken());
            
        userToken.setUser(user);
        userToken.setToken(token);
        userToken.setExpiryTime(LocalDateTime.now().plusMinutes(1));
        
        userTokenRepository.save(userToken);
        
        return token;
    }
}