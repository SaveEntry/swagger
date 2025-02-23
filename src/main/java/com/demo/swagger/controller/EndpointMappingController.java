package com.demo.swagger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.swagger.service.EndpointMappingService;
import com.demo.swagger.dto.EndpointInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/endpoints")
@Tag(name = "Endpoint Mapping", description = "APIs for retrieving endpoint information")
public class EndpointMappingController {

    @Autowired
    private EndpointMappingService endpointMappingService;

    @GetMapping
    @Operation(summary = "List all API endpoints", description = "Returns a list of all available API endpoints with their roles and methods")
    public ResponseEntity<List<EndpointInfoDTO>> getAllEndpoints() {
        // Add debug logging
        List<EndpointInfoDTO> endpoints = endpointMappingService.getAllEndpoints();
        System.out.println("Controller returning " + endpoints.size() + " endpoints");
        return ResponseEntity.ok(endpoints);
    }
}