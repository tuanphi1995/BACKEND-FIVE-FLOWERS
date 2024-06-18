package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Users;
import com.example.backendfiveflowers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React app

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Users registerUser(@RequestBody Users user) {
        return userService.saveUser(user);
    }

    @PostMapping("/role")
    public void addRoleToUser(@RequestParam String username, @RequestParam String roleName) {
        userService.addRoleToUser(username, roleName);
    }
}
