package com.maxmarkovdev.springboot.dao.impl;
import com.maxmarkovdev.springboot.dao.impl.astracts.ReadWriteDaoImpl;
import com.maxmarkovdev.springboot.dao.interfaces.RoleDao;
import com.maxmarkovdev.springboot.dao.util.SingleResultUtil;
import com.maxmarkovdev.springboot.model.Role;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class RoleDaoImpl extends ReadWriteDaoImpl<Role, Long> implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Role> findByName(String name) {
        return SingleResultUtil.getSingleResultOrNull(entityManager
                .createQuery("from Role where role = :name", Role.class)
                .setParameter("name", name));
    }

    @PersistenceUnit
    private SessionFactory sessionFactory;

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
