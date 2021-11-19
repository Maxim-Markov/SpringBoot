package com.maxmarkovdev.springboot.mapper;

import com.maxmarkovdev.springboot.dto.UserDTO;
import com.maxmarkovdev.springboot.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring",uses = {RoleMapper.class})
public interface UserMapper {
    @Mapping(source = "user.password", target = "pass")
    UserDTO toDto(User user);

    @InheritInverseConfiguration
    User toModel(UserDTO userDTO);

    List<UserDTO> toDto(List<User> users);

    List<User> toModel(List<UserDTO> userDTO);
}
