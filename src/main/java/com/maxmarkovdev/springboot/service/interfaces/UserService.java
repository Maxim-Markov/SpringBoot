package com.maxmarkovdev.springboot.service.interfaces;


import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.service.interfaces.abstracts.ReadWriteService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService extends ReadWriteService<User, Long> {
    Optional<User> findByEmail(String email);

    void changePasswordById(Long id, String password);

    long createUser(User user);

    UserDetails loadUserByUsername(String s);

    void updateById(long id, User user);

    @Override
    List<User> getAll();

}