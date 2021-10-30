package com.maxmarkovdev.springboot.dao;

import com.maxmarkovdev.springboot.model.User;

import java.util.List;


public interface UserDao {
    User getUserByName(String name);
    void createUser(User user);

    List<User> getUsers();

    User getUser(long id);

    void updateUser(long id, User user);

    void deleteUser(long id);
}
