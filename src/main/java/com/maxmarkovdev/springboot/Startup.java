package com.maxmarkovdev.springboot;


import com.maxmarkovdev.springboot.service.RoleService;
import com.maxmarkovdev.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Startup {

    UserService service;
    RoleService roleService;


    @Autowired
    public void setService(UserService service, RoleService roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    @Bean
    public void init() {
        System.out.println("Startup initializing");
    }
}