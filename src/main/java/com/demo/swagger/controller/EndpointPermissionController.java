// src/main/java/com/demo/swagger/controller/EndpointPermissionController.java
package com.demo.swagger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.demo.swagger.dto.EndpointInfoDTO;
import com.demo.swagger.dto.EndpointPermissionUpdateDTO;
import com.demo.swagger.service.EndpointPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/permissions")
@Tag(name = "Endpoint Permissions", description = "APIs for managing endpoint permissions")
public class EndpointPermissionController {
    
    @Autowired
    private EndpointPermissionService endpointPermissionService;
    
    @GetMapping
    @Operation(summary = "List all endpoint permissions", 
               description = "Returns a list of all API endpoints with their allowed roles")
    public ResponseEntity<List<EndpointInfoDTO>> getAllEndpointPermissions() {
        return ResponseEntity.ok(endpointPermissionService.getAllEndpointPermissions());
    }
    
    @GetMapping("/{method}/{path}")
    @Operation(summary = "Get endpoint permissions by path", 
               description = "Retrieves permission details for a specific endpoint")
    public ResponseEntity<EndpointInfoDTO> getEndpointPermission(
            @PathVariable String method,
            @PathVariable String path) {
        return endpointPermissionService.getEndpointPermission(path, method)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/update")
    @Operation(summary = "Update endpoint permissions", 
               description = "Updates the allowed roles for a specific endpoint")
    public ResponseEntity<?> updateEndpointPermission(
            @Valid @RequestBody EndpointPermissionUpdateDTO updateDTO) {
        try {
            EndpointInfoDTO updated = endpointPermissionService.updateEndpointPermission(updateDTO);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error updating endpoint permission: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @PostMapping("/initialize")
    @Operation(summary = "Initialize endpoint permissions", 
               description = "Scans all available endpoints and initializes permission records")
    public ResponseEntity<?> initializeEndpointPermissions() {
        try {
            endpointPermissionService.initializeEndpointPermissions();
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Endpoint permissions initialized successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error initializing endpoint permissions: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }
}