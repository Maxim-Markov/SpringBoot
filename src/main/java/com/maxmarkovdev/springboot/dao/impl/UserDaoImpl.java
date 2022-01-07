package com.maxmarkovdev.springboot.dao.impl;

import com.maxmarkovdev.springboot.dao.impl.astracts.ReadWriteDaoImpl;
import com.maxmarkovdev.springboot.dao.interfaces.UserDao;
import com.maxmarkovdev.springboot.dao.util.SingleResultUtil;
import com.maxmarkovdev.springboot.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl extends ReadWriteDaoImpl<User, Long> implements UserDao {

    @PersistenceUnit
    private SessionFactory sessionFactory;

    @Override
    public Optional<User> findByName(String name) {
        String hql = "FROM User u JOIN FETCH u.roles WHERE u.name = :name AND u.isEnabled = true";
        TypedQuery<User> query = sessionFactory.getCurrentSession()
                .createQuery(hql, User.class).setParameter("name", name);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public void changePassword(String username, String password) {
        String hql = "UPDATE User SET password = :passwordParam WHERE name = :nameParam";
        sessionFactory.getCurrentSession().createQuery(hql)
                .setParameter("passwordParam", password)
                .setParameter("nameParam", username)
                .executeUpdate();
    }

    @Override
    public Optional<User> getUserByName(String name) {
        String hql = "FROM User u JOIN FETCH u.roles WHERE u.name = :userName AND u.isEnabled = true";
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql,User.class).setParameter("userName", name);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public long createUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        long id = (long) session.save(user);
        System.out.println("User с именем – " + user.getName() + " добавлен в базу данных");
        return id;
    }

    @Override
    public void updateById(long id, User user) {
        user.setId(id);
        super.update(user);
    }

    @Override
    public List<User> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM User u JOIN FETCH u.roles WHERE u.isEnabled = true ORDER BY u.id", User.class)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        User user = sessionFactory.getCurrentSession().find(User.class, id);
        user.setIsEnabled(false);
    }
}

