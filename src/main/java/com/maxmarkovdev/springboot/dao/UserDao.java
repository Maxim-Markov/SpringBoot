package com.maxmarkovdev.springboot.dao;

import com.maxmarkovdev.springboot.model.User;

import java.util.List;
import java.util.Optional;


public interface UserDao {
    Optional<User> getUserByName(String name);

    long createUser(User user);

    List<User> getUsers();

    User getUser(long id);

    void updateUser(long id, User user);

    void deleteUser(long id);
}
