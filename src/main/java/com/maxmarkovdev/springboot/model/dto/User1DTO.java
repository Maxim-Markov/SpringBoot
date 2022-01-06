package com.maxmarkovdev.springboot.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maxmarkovdev.springboot.model.dto.RoleDTO;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@NoArgsConstructor
public class User1DTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;

    private String lastName;

    private String age;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pass;

    private List<RoleDTO> roles;


}

