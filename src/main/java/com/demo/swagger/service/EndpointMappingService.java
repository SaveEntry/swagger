package com.demo.swagger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import com.demo.swagger.dto.EndpointInfoDTO;
import com.demo.swagger.enums.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.lang.reflect.Method;

@Service
public class EndpointMappingService {
    
    @Autowired
    private ApplicationContext applicationContext;

    public List<EndpointInfoDTO> getAllEndpoints() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
        List<EndpointInfoDTO> endpoints = new ArrayList<>();

        // Add debug logging
        System.out.println("Found " + handlerMethods.size() + " handler methods");

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            
            // Add debug logging
            System.out.println("Processing mapping: " + mappingInfo);
            
            if (mappingInfo.getPatternsCondition() != null) {
                Set<String> patterns = mappingInfo.getPatternsCondition().getPatterns();
                // Add debug logging
                System.out.println("Found patterns: " + patterns);
                
                for (String path : patterns) {
                    String method = getHttpMethod(mappingInfo);
                    Set<UserRole> roles = new HashSet<>(Collections.singleton(UserRole.ADMIN));
                    String description = getDescription(handlerMethod.getMethod());
                    String controllerName = handlerMethod.getBeanType().getSimpleName();

                    // Add debug logging
                    System.out.println("Adding endpoint: " + path + " " + method);

                    endpoints.add(new EndpointInfoDTO(
                        path,
                        method,
                        roles,
                        description,
                        controllerName
                    ));
                }
            }
        }

        // Add debug logging
        System.out.println("Returning " + endpoints.size() + " endpoints");
        return endpoints;
    }

    private String getHttpMethod(RequestMappingInfo mappingInfo) {
        if (mappingInfo.getMethodsCondition() == null || 
            mappingInfo.getMethodsCondition().getMethods().isEmpty()) {
            return "ALL";
        }
        return mappingInfo.getMethodsCondition().getMethods().iterator().next().name();
    }

    private String getDescription(Method method) {
        Operation operation = method.getAnnotation(Operation.class);
        if (operation != null && !operation.summary().isEmpty()) {
            return operation.summary();
        }
        return "No description available";
    }
}