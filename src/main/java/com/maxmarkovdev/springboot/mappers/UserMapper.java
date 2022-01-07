package com.maxmarkovdev.springboot.mappers;

import com.maxmarkovdev.springboot.model.dto.User1DTO;
import com.maxmarkovdev.springboot.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    @Mapping(source = "user.password", target = "pass")
    User1DTO toDto(User user);

    @InheritInverseConfiguration
    User toModel(User1DTO userDTO);

    List<User1DTO> toDto(List<User> users);

    List<User> toModel(List<User1DTO> userDTO);
}
