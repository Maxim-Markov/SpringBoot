package com.maxmarkovdev.springboot.service.impl.dto;

import com.maxmarkovdev.springboot.dao.interfaces.dto.UserDtoDao;
import com.maxmarkovdev.springboot.model.dto.UserDto;
import com.maxmarkovdev.springboot.service.interfaces.dto.UserDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDtoServiceImpl extends PageDtoServiceImpl<UserDto> implements UserDtoService {

    private final UserDtoDao userDtoDao;

    @Autowired
    public UserDtoServiceImpl(UserDtoDao userDtoDao) {
        this.userDtoDao = userDtoDao;
    }

    @Override
    @Transactional
    public Optional<UserDto> getUserDtoById(Long id) {
        return userDtoDao.getUserDtoById(id);
    }
}
