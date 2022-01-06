package com.maxmarkovdev.springboot.dao.interfaces;

import com.maxmarkovdev.springboot.dao.interfaces.abstracts.ReadWriteDao;
import com.maxmarkovdev.springboot.model.User;

import java.util.List;
import java.util.Optional;


public interface UserDao extends ReadWriteDao<User, Long> {
    Optional<User> findByName(String email);

    void changePassword(String username, String password);

    Optional<User> getUserByName(String name);

    long createUser(User user);

    void updateById(long id, User user);

    @Override
    List<User> getAll();
}
