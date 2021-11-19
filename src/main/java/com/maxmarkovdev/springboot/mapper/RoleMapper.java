package com.maxmarkovdev.springboot.mapper;

import com.maxmarkovdev.springboot.dto.RoleDTO;
import com.maxmarkovdev.springboot.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO toDto(Role role);

    Role toModel(RoleDTO roleDTO);

}
