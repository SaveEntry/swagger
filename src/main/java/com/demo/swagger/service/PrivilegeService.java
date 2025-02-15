package com.demo.swagger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.swagger.model.Privilege;
import com.demo.swagger.model.User;
import com.demo.swagger.repository.PrivilegeRepository;
import com.demo.swagger.repository.UserRepository;
import com.demo.swagger.enums.UserRole;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Privilege createInitialPrivilege(User user) {
        Privilege privilege = new Privilege();
        privilege.setUser(user);
        privilege.setUserRole(UserRole.USER);
        return privilegeRepository.save(privilege);
    }
    
    public Privilege updateUserPrivilege(Long userId, UserRole newRole) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
            
        Privilege privilege = privilegeRepository.findByUser(user)
            .orElseThrow(() -> new EntityNotFoundException("Privilege not found for user"));
            
        privilege.setUserRole(newRole);
        return privilegeRepository.save(privilege);
    }
    
    public Privilege getUserPrivilege(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
            
        return privilegeRepository.findByUser(user)
            .orElseThrow(() -> new EntityNotFoundException("Privilege not found for user"));
    }
}