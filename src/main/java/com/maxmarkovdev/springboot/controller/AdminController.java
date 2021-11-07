package com.maxmarkovdev.springboot.controller;


import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAdminPage(@AuthenticationPrincipal User currentUser, @ModelAttribute("user") User user, ModelMap model) {
        model.addAttribute("users", userService.getUsers());
        User userFromDB = (User) userService.loadUserByUsername(currentUser.getUsername());
        model.addAttribute("currentUser", userFromDB);
        return "admin";
    }




}

