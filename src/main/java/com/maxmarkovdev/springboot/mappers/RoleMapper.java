package com.maxmarkovdev.springboot.mappers;

import com.maxmarkovdev.springboot.model.Role;
import com.maxmarkovdev.springboot.model.dto.RoleDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO toDto(Role role);

    Role toModel(RoleDTO roleDTO);
}
