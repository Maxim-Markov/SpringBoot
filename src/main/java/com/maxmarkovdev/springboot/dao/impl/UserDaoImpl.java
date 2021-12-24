package com.maxmarkovdev.springboot.dao.impl;

import com.maxmarkovdev.springboot.dao.impl.astracts.ReadWriteDaoImpl;
import com.maxmarkovdev.springboot.dao.interfaces.UserDao;
import com.maxmarkovdev.springboot.dao.util.SingleResultUtil;
import com.maxmarkovdev.springboot.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl extends ReadWriteDaoImpl<User, Long> implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public Optional<User> findByEmail(String email) {
        String hql = "FROM User u JOIN FETCH u.roles WHERE u.email = :email";
        TypedQuery<User> query = (TypedQuery<User>) entityManager.createQuery(hql).setParameter("email", email);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public void changePassword(Long id, String password) {
        String hql = "update User set password = :passwordParam where id = :idParam";
        entityManager.createQuery(hql)
                .setParameter("passwordParam", password)
                .setParameter("idParam", id)
                .executeUpdate();
    }

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
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

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsers() {
        return (List<User>) sessionFactory.getCurrentSession()
                .createQuery("from User").getResultList();
    }

    @Override
    public User getUser(long id) {
        return sessionFactory.getCurrentSession()
                .find(User.class, id);
    }

    @Override
    public void updateUser(long id, User user) {
        user.setId(id);
        sessionFactory.getCurrentSession().merge(user);
    }

    @Override
    public void deleteUser(long id) {
        User userFromDB = sessionFactory.getCurrentSession()
                .find(User.class, id);
            sessionFactory.getCurrentSession().remove(userFromDB);
    }
}

