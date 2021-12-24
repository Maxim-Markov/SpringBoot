package com.maxmarkovdev.springboot.controller;

import com.maxmarkovdev.springboot.mapper.UserMapper;
import com.maxmarkovdev.springboot.model.Role;
import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.dto.UserDTO;
import com.maxmarkovdev.springboot.service.interfaces.RoleService;
import com.maxmarkovdev.springboot.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class RestControllers {

    private UserMapper userMapper;
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService, RoleService roleService, UserMapper userMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal User currentUser, @PathVariable("id") long id) {
        if (id == currentUser.getId()) {
            return new ResponseEntity<>("You cannot delete yourself", HttpStatus.BAD_REQUEST);
        }
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserDTO user) {
        Role userRole = roleService.getRoleByName(user.getRoles().get(0).getRole());
        User userEntity = userMapper.toModel(user);
        userEntity.getRoles().clear();
        userEntity.addRole(userRole);
        try {
            long id = userService.createUser(userEntity);
            userEntity.setId(id);
            return ResponseEntity.ok().body(userMapper.toDto(userEntity));
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("user with such name already exists", HttpStatus.BAD_REQUEST);
        }
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
        if (id == currentUser.getId()) {
            return new ResponseEntity<>("You cannot delete yourself", HttpStatus.BAD_REQUEST);
        }
        Role userRole = roleService.getRoleByName(role);
        User user = new User(name, password, email, lastName, Byte.parseByte(age));
        user.addRole(userRole);
        userService.updateUser(id, user);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDTO>> getAllUser() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(userMapper.toDto(users), HttpStatus.OK);
    }
}
