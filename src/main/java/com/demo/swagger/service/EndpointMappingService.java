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

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            
            // Get the request path
            String path = mappingInfo.getPatternValues().stream()
                .findFirst()
                .orElse("");
                
            if (!path.isEmpty()) {
                String method = getHttpMethod(mappingInfo);
                Set<UserRole> roles = new HashSet<>(Collections.singleton(UserRole.ADMIN));
                String description = getDescription(handlerMethod.getMethod());
                String controllerName = handlerMethod.getBeanType().getSimpleName();

                endpoints.add(new EndpointInfoDTO(
                    path,
                    method,
                    roles,
                    description,
                    controllerName
                ));
            }
        }

        return endpoints;
    }

    private String getHttpMethod(RequestMappingInfo mappingInfo) {
        Set<String> methods = mappingInfo.getMethodsCondition().getMethods().stream()
            .map(Enum::name)
            .collect(java.util.stream.Collectors.toSet());
            
        return methods.isEmpty() ? "ALL" : String.join(", ", methods);
    }

    private String getDescription(Method method) {
        Operation operation = method.getAnnotation(Operation.class);
        if (operation != null && !operation.summary().isEmpty()) {
            return operation.summary();
        }
        return "No description available";
    }
}