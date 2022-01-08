package com.maxmarkovdev.springboot.controllers;
import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired()
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAdminPage(@AuthenticationPrincipal User currentUser, @ModelAttribute("user") User user, ModelMap model) {
        model.addAttribute("users", userService.getAll());
        User userFromDB = (User) userService.loadUserByUsername(currentUser.getUsername());
        model.addAttribute("currentUser", userFromDB);
        return "admin";
    }




}

