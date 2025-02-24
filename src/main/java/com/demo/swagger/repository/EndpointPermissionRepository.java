// src/main/java/com/demo/swagger/repository/EndpointPermissionRepository.java
package com.demo.swagger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.swagger.model.EndpointPermission;
import java.util.Optional;

public interface EndpointPermissionRepository extends JpaRepository<EndpointPermission, Long> {
    Optional<EndpointPermission> findByPathAndMethod(String path, String method);
    boolean existsByPathAndMethod(String path, String method);
}