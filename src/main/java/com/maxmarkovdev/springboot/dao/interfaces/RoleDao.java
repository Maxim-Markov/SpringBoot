package com.maxmarkovdev.springboot.dao.interfaces;

import com.maxmarkovdev.springboot.dao.interfaces.abstracts.ReadWriteDao;
import com.maxmarkovdev.springboot.model.Role;

import java.util.Optional;

public interface RoleDao extends ReadWriteDao<Role, Long> {
    Optional<Role> findByName(String name);
    Role getRoleByName(String role);
    void createRole(Role role);
}
