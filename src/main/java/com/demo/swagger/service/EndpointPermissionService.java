// src/main/java/com/demo/swagger/service/EndpointPermissionService.java
package com.demo.swagger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.swagger.dto.EndpointInfoDTO;
import com.demo.swagger.dto.EndpointPermissionUpdateDTO;
import com.demo.swagger.enums.UserRole;
import com.demo.swagger.model.EndpointPermission;
import com.demo.swagger.repository.EndpointPermissionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EndpointPermissionService {
    
    @Autowired
    private EndpointPermissionRepository endpointPermissionRepository;
    
    @Autowired
    private EndpointMappingService endpointMappingService;
    
    /**
     * Initialize endpoint permissions from detected endpoints
     */
    @Transactional
    public void initializeEndpointPermissions() {
        List<EndpointInfoDTO> endpoints = endpointMappingService.getAllEndpoints();
        
        for (EndpointInfoDTO endpoint : endpoints) {
            if (!endpointPermissionRepository.existsByPathAndMethod(endpoint.getPath(), endpoint.getMethod())) {
                EndpointPermission permission = new EndpointPermission();
                permission.setPath(endpoint.getPath());
                permission.setMethod(endpoint.getMethod());
                permission.setDescription(endpoint.getDescription());
                permission.setControllerName(endpoint.getControllerName());
                
                // By default, only ADMIN role can access all endpoints
                permission.getAllowedRoles().add(UserRole.ADMIN);
                
                endpointPermissionRepository.save(permission);
            }
        }
    }
    
    /**
     * Get all endpoint permissions
     */
    public List<EndpointInfoDTO> getAllEndpointPermissions() {
        return endpointPermissionRepository.findAll().stream()
                .map(ep -> new EndpointInfoDTO(
                        ep.getPath(),
                        ep.getMethod(),
                        ep.getAllowedRoles(),
                        ep.getDescription(),
                        ep.getControllerName()))
                .collect(Collectors.toList());
    }
    
    /**
     * Update endpoint permissions
     */
    @Transactional
    public EndpointInfoDTO updateEndpointPermission(EndpointPermissionUpdateDTO updateDTO) {
        EndpointPermission permission = endpointPermissionRepository
                .findByPathAndMethod(updateDTO.getPath(), updateDTO.getMethod())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Endpoint not found: " + updateDTO.getMethod() + " " + updateDTO.getPath()));
        
        permission.setAllowedRoles(updateDTO.getAllowedRoles());
        permission = endpointPermissionRepository.save(permission);
        
        return new EndpointInfoDTO(
                permission.getPath(),
                permission.getMethod(),
                permission.getAllowedRoles(),
                permission.getDescription(),
                permission.getControllerName());
    }
    
    /**
     * Get endpoint permission by path and method
     */
    public Optional<EndpointInfoDTO> getEndpointPermission(String path, String method) {
        return endpointPermissionRepository.findByPathAndMethod(path, method)
                .map(ep -> new EndpointInfoDTO(
                        ep.getPath(),
                        ep.getMethod(),
                        ep.getAllowedRoles(),
                        ep.getDescription(),
                        ep.getControllerName()));
    }
}