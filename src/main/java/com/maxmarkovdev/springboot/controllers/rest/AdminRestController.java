package com.maxmarkovdev.springboot.controllers.rest;

import com.maxmarkovdev.springboot.model.dto.User1DTO;
import com.maxmarkovdev.springboot.mappers.UserMapper;
import com.maxmarkovdev.springboot.model.Role;
import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.service.interfaces.RoleService;
import com.maxmarkovdev.springboot.service.interfaces.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminRestController {

    private final UserMapper userMapper;
    private final UserService userService;
    private final RoleService roleService;

    public AdminRestController(UserMapper userMapper, UserService userService, RoleService roleService) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.roleService = roleService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal User currentUser, @PathVariable("id") long id) {
        if (id != currentUser.getId()) {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("You cannot delete yourself", HttpStatus.BAD_REQUEST);
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User1DTO user) {
        Role userRole = roleService.findRoleByName(user.getRoles().get(0).getRole()).orElse(null);
        User userEntity = userMapper.toModel(user);
        userEntity.setRoles(Collections.singleton(userRole));
        long id;
        try {
            id = userService.createUser(userEntity);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("user with such name already exists", HttpStatus.BAD_REQUEST);
        }
        userEntity.setId(id);
        return ResponseEntity.ok().body(userMapper.toDto(userEntity));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal User currentUser,
                                        @RequestParam("name") String name,
                                        @RequestParam("lastName") String lastName,
                                        @RequestParam("age") String age,
                                        @RequestParam("email") String email,
                                        @RequestParam("password") String password,
                                        @RequestParam("roles") String role,
                                        @PathVariable("id") long id) {
        if (id != currentUser.getId()) {
            Role userRole = roleService.findRoleByName(role).orElse(null);
            User user = new User(name, password, email, lastName, Byte.parseByte(age));
            user.addRole(userRole);
            userService.updateById(id, user);
            return ResponseEntity.ok(userMapper.toDto(user));
        }
        return new ResponseEntity<>("You update delete yourself", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User1DTO>> getAllUser() {
        List<User> users = userService.getAll();
        return new ResponseEntity<>(userMapper.toDto(users), HttpStatus.OK);
    }
}
