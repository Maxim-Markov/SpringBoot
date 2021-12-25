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
    public Optional<User> findByEmail(String email) {
        String hql = "FROM User u JOIN FETCH u.roles WHERE u.email = :email";
        TypedQuery<User> query = sessionFactory.getCurrentSession()
                .createQuery(hql,User.class).setParameter("email", email);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public void changePassword(Long id, String password) {
        String hql = "UPDATE User SET password = :passwordParam WHERE id = :idParam";
        sessionFactory.getCurrentSession().createQuery(hql)
                .setParameter("passwordParam", password)
                .setParameter("idParam", id)
                .executeUpdate();
    }

    @Override
    public Optional<User> getUserByName(String name) {
        String hql = "FROM User u JOIN FETCH u.roles WHERE u.name = :userName";
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
                .createQuery("FROM User u JOIN FETCH u.roles", User.class)
                .getResultList();
    }
}

