// src/main/java/com/demo/swagger/config/SecurityConfig.java
package com.demo.swagger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.demo.swagger.service.EndpointPermissionService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private EndpointPermissionService endpointPermissionService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
    
    @Bean
    public CommandLineRunner initializePermissions() {
        return args -> {
            // Initialize endpoint permissions on application startup
            endpointPermissionService.initializeEndpointPermissions();
        };
    }
}