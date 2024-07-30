package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Page<UserInfo> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userInfoService.getAllUsers(pageable);
    }

    @GetMapping("/getAllAdmins")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Page<UserInfo> getAllAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userInfoService.getAllAdmins(pageable);
    }

    @GetMapping("/getUserById/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserInfo getUserById(@PathVariable int id) {
        return userInfoService.getUserById(id);
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserInfo updateUser(@PathVariable int id, @RequestBody UserInfo userInfo) {
        userInfo.setId(id);
        return userInfoService.updateUser(userInfo);
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteUser(@PathVariable int id) {
        userInfoService.deleteUser(id);
        return "User deleted successfully";
    }
}
