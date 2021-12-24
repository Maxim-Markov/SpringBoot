package com.maxmarkovdev.springboot.service.impl;

import com.maxmarkovdev.springboot.dao.interfaces.UserDao;
import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.service.impl.abstracts.ReadWriteServiceImpl;
import com.maxmarkovdev.springboot.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends ReadWriteServiceImpl<User, Long> implements UserDetailsService, UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        super(userDao);
        this.userDao = userDao;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void changePasswordById(Long id, String password) {
        String passHash = BCrypt.hashpw(password, BCrypt.gensalt());
        userDao.changePassword(id, passHash);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDao.getUserByName(s)
                .orElseThrow(() -> new UsernameNotFoundException("User with name: " + s + " not found"));
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    @Transactional
    public User getUser(long id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void updateUser(long id, User user) {
        userDao.updateUser(id, user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public long createUser(User user) {
        return userDao.createUser(user);
    }
}
