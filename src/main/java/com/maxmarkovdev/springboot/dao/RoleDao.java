package com.maxmarkovdev.springboot.dao;

import com.maxmarkovdev.springboot.model.Role;

public interface RoleDao {
    Role getRoleByName(String role);
    void createRole(Role role);
}
