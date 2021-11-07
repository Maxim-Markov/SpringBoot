package com.maxmarkovdev.springboot.controller;

import com.maxmarkovdev.springboot.model.Role;
import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class RestControllers {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{id}")
    public String DeleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "Deleted";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> createUser(@RequestParam("name") String name,
                                        @RequestParam("lastName") String lastName,
                                        @RequestParam("age") String age,
                                        @RequestParam("email") String email,
                                        @RequestParam("password") String password,
                                        @RequestParam("roles") String role) {
        Role userRole = new Role(role);
        User user = new User(name,lastName,Byte.parseByte(age),email,password);
        user.addRole(userRole);
        long id = userService.createUser(user);
        user.setId(id);
        return ResponseEntity.ok().body(user);
    }
}
