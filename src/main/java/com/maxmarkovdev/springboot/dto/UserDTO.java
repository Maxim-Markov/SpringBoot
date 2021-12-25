package com.maxmarkovdev.springboot.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@NoArgsConstructor
public class UserDTO {

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

