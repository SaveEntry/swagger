package com.demo.swagger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.demo.swagger.dto.SignInDTO;
import com.demo.swagger.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "APIs for user authentication")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @PostMapping("/signin")
    @Operation(summary = "Sign in user", description = "Authenticates user and returns JWT token")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInDTO signInDTO) {
        String token = authService.signIn(signInDTO);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}