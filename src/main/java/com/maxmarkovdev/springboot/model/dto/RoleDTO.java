package com.maxmarkovdev.springboot.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class RoleDTO {
    private String role;
}
