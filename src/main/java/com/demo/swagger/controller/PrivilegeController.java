package com.demo.swagger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.demo.swagger.service.PrivilegeService;
import com.demo.swagger.dto.PrivilegeUpdateDTO;
import com.demo.swagger.model.Privilege;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/privileges")
@Tag(name = "Privilege Management", description = "APIs for managing user privileges")
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;
    
    @GetMapping("/{userId}")
    @Operation(summary = "Get user privileges", description = "Retrieves privileges for a specific user")
    public ResponseEntity<Privilege> getUserPrivilege(@PathVariable Long userId) {
        return ResponseEntity.ok(privilegeService.getUserPrivilege(userId));
    }
    
    @PutMapping("/{userId}")
    @Operation(summary = "Update user privileges", description = "Updates privileges for a specific user")
    public ResponseEntity<Privilege> updateUserPrivilege(
            @PathVariable Long userId,
            @Valid @RequestBody PrivilegeUpdateDTO privilegeUpdateDTO) {
        return ResponseEntity.ok(
            privilegeService.updateUserPrivilege(userId, privilegeUpdateDTO.getUserRole())
        );
    }
}