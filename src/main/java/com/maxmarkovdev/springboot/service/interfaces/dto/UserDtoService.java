package com.maxmarkovdev.springboot.service.interfaces.dto;

import com.maxmarkovdev.springboot.model.dto.UserDto;

import java.util.Optional;

public interface UserDtoService extends PageDtoService<UserDto> {
    Optional<UserDto> getUserDtoById(Long id);
}
