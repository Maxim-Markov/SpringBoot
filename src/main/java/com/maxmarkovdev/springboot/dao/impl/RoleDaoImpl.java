package com.maxmarkovdev.springboot.dao.impl;

import com.maxmarkovdev.springboot.dao.impl.astracts.ReadWriteDaoImpl;
import com.maxmarkovdev.springboot.dao.interfaces.RoleDao;
import com.maxmarkovdev.springboot.dao.util.SingleResultUtil;
import com.maxmarkovdev.springboot.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class RoleDaoImpl extends ReadWriteDaoImpl<Role, Long> implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Role> findByName(String name) {
        return SingleResultUtil.getSingleResultOrNull(entityManager
                .createQuery("FROM Role WHERE role = :name", Role.class)
                .setParameter("name", name));
    }
}
