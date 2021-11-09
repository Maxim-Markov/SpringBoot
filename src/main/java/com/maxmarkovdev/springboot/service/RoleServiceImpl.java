package com.maxmarkovdev.springboot.service;

import com.maxmarkovdev.springboot.dao.RoleDao;
import com.maxmarkovdev.springboot.dao.UserDao;
import com.maxmarkovdev.springboot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;

    @Autowired
    public void setDao(RoleDao dao) {
        this.roleDao = dao;
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
