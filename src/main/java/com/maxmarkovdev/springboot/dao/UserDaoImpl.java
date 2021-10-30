package com.maxmarkovdev.springboot.dao;

import com.maxmarkovdev.springboot.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User getUserByName(String name) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from User user where user.name = :userName");
        query.setParameter("userName", name);
        return (User) query.getSingleResult();
    }



    @Override
    public void createUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        System.out.println("User с именем – " + user.getName() + " добавлен в базу данных");
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
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
    public void deleteUser(long id) {
        User userFromDB = sessionFactory.getCurrentSession()
                .find(User.class, id);
        if (userFromDB != null) {
            userFromDB.setRoles(null);
            sessionFactory.getCurrentSession().remove(userFromDB);
        }
    }
}

