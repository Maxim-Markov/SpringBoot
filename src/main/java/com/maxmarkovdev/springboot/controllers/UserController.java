package com.maxmarkovdev.springboot.controllers;


import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.service.interfaces.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/user1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getUserPage(@AuthenticationPrincipal User user, Model modelMap) {
        User userFromDB = (User) userService.loadUserByUsername(user.getUsername());
        modelMap.addAttribute("user", userFromDB);
        return "user";
    }
}
