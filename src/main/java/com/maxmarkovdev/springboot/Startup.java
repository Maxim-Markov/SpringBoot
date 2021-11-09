package com.maxmarkovdev.springboot;


import com.maxmarkovdev.springboot.model.Role;
import com.maxmarkovdev.springboot.model.User;
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
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        Role role3 = new Role("ROLE_UNDEFINED");
        roleService.createRole(role1);
        roleService.createRole(role2);
        roleService.createRole(role3);

        User user1 = new User("ADMIN",  "sws", (byte) 36, "dg@xsjb.com", "admin");
        User user2 = new User("test",  "sws", (byte) 33, "dg@xsjb.com", "test");
        User user3 = new User("tr",  "sws", (byte) 39, "dg@xsjb.com", "admin");
        user1.addRole(role1);
        user2.addRole(role2);
        user2.addRole(role3);
        user3.addRole(role1);
        service.createUser(user1);
        service.createUser(user2);
        service.createUser(user3);
    }
}