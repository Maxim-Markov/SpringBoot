package com.maxmarkovdev.springboot.service;

import com.maxmarkovdev.springboot.model.Role;
import com.maxmarkovdev.springboot.model.User;
public interface RoleService {
    Role getRoleByName(String role);
    void createRole(Role role);
}
