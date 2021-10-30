package com.maxmarkovdev.springboot.service;


import com.maxmarkovdev.springboot.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    void createUser(User user);

    UserDetails loadUserByUsername(String s);

    List<User> getUsers();

    User getUser(long id);

    void updateUser(long id, User user);

    void deleteUser(long id);
}