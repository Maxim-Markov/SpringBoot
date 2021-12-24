package com.maxmarkovdev.springboot.service.interfaces;

import com.maxmarkovdev.springboot.model.Role;
import com.maxmarkovdev.springboot.service.interfaces.abstracts.ReadWriteService;

import java.util.Optional;

public interface RoleService extends ReadWriteService<Role, Long> {
    Optional<Role> findRoleByName(String name);
    Role getRoleByName(String role);
    void createRole(Role role);
}
