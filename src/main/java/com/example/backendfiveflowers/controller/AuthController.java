package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.AuthResponse;
import com.example.backendfiveflowers.entity.User;
import com.example.backendfiveflowers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React app

public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        User authenticatedUser = userService.getUser(user.getUserName());
        return new AuthResponse(authenticatedUser.getUserName(), authenticatedUser.getEmail(), roles);
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/register/admin")
    public User registerAdmin(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        userService.addRoleToUser(savedUser.getUserName(), "ADMIN");
        return savedUser;
    }
}
