package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/getUsers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserInfo> getAllUsers() {
        return userInfoService.getAllUser();
    }

    @GetMapping("/getUser/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserInfo getUserById(@PathVariable int id) {
        return userInfoService.getUserById(id);
    }
}
