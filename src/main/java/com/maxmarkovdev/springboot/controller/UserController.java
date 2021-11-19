package com.maxmarkovdev.springboot.controller;


import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getUserPage(@AuthenticationPrincipal User user, Model modelMap) {
        User userFromDB = (User) userService.loadUserByUsername(user.getUsername());
        modelMap.addAttribute("user", userFromDB);
        return "user";
    }
}
