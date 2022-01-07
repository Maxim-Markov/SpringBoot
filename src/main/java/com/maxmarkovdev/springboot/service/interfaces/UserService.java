package com.maxmarkovdev.springboot.service.interfaces;


import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.service.interfaces.abstracts.ReadWriteService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService extends ReadWriteService<User, Long> {
    Optional<User> findByName(String name);

    void changePasswordByName(String username, String password);

    long createUser(User user);

    UserDetails loadUserByUsername(String s);

    void updateById(long id, User user);

    @Override
    List<User> getAll();

    @Override
    void deleteById(Long id);
}