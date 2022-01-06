package com.maxmarkovdev.springboot.dao.interfaces.dto;

import com.maxmarkovdev.springboot.model.dto.UserDto;

import java.util.Optional;

public interface UserDtoDao {
    public Optional<UserDto> getUserDtoById(Long id);
}
