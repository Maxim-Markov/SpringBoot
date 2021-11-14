package com.maxmarkovdev.springboot.controller;

import com.maxmarkovdev.springboot.model.Role;
import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.service.RoleService;
import com.maxmarkovdev.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class RestControllers {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
    public ResponseEntity<?> createUser(@RequestBody User user) {
        Role userRole = roleService.getRoleByName(user.getRoles().stream().findFirst().orElseThrow().getRole());
        user.getRoles().clear();
        user.addRole(userRole);
        try {
            long id = userService.createUser(user);
            user.setId(id);
            return ResponseEntity.ok().body(user);
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
        User user = new User(name, lastName, Byte.parseByte(age), email, password);
        user.addRole(userRole);
        userService.updateUser(id, user);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
