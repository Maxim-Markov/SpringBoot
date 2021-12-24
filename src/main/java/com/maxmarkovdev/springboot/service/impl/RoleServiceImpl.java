package com.maxmarkovdev.springboot.service.impl;

import com.maxmarkovdev.springboot.dao.interfaces.RoleDao;
import com.maxmarkovdev.springboot.model.Role;
import com.maxmarkovdev.springboot.service.impl.abstracts.ReadWriteServiceImpl;
import com.maxmarkovdev.springboot.service.interfaces.RoleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RoleServiceImpl extends ReadWriteServiceImpl<Role, Long> implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        super(roleDao);
        this.roleDao = roleDao;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Optional<Role> findRoleByName(String name) {
        return roleDao.findByName(name);
    }

    @Transactional
    @Override
    public Role getRoleByName(String role) {
        return roleDao.getRoleByName(role);
    }

    @Transactional
    @Override
    public void createRole(Role role) {
        roleDao.createRole(role);

    }
}
