package com.maxmarkovdev.springboot.controller;


import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String UpdateUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String DeleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

