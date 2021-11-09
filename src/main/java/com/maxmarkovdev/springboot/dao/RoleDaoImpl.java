package com.maxmarkovdev.springboot.dao;
import com.maxmarkovdev.springboot.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceUnit
    SessionFactory sessionFactory;

    @Override
    public Role getRoleByName(String role) {
        TypedQuery<Role> result = sessionFactory.getCurrentSession().createQuery("FROM Role WHERE role=:userRole", Role.class).setParameter("userRole", role);
        return result.getResultList().isEmpty() ? null : result.getSingleResult();
    }

    @Override
    public void createRole(Role role) {
        sessionFactory.getCurrentSession().persist(role);
    }
}
