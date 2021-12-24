//package com.maxmarkovdev.springboot.configs;
//
//import com.maxmarkovdev.springboot.model.Role;
//import com.maxmarkovdev.springboot.model.User;
//import com.maxmarkovdev.springboot.service.interfaces.RoleService;
//import com.maxmarkovdev.springboot.service.interfaces.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Component
//public class DbInit {
//
//    UserService service;
//    RoleService roleService;
//
//    @Autowired
//    public void setService(UserService service, RoleService roleService) {
//        this.service = service;
//        this.roleService = roleService;
//    }
//
//    @PostConstruct
//    private void postConstruct() {
//        Role role1 = new Role("ROLE_ADMIN");
//        Role role2 = new Role("ROLE_USER");
//        Role role3 = new Role("ROLE_UNDEFINED");
//        roleService.createRole(role1);
//        roleService.createRole(role2);
//        roleService.createRole(role3);
//
//        User user1 = new User("ADMIN", "admin", "dg@xsjb.com","sws", (byte) 36);
//        User user2 = new User("test", "test", "dg@xsjb.com","sws", (byte) 36);
//        User user3 = new User("u", "u", "dg@xsjb.com","sws", (byte) 36);
//        user1.addRole(role1);
//        user2.addRole(role2);
//        user2.addRole(role3);
//        user3.addRole(role1);
//        service.createUser(user1);
//        service.createUser(user2);
//        service.createUser(user3);
//    }
//}
