package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.UserRoles;
import com.example.backendfiveflowers.entity.Users;
import com.example.backendfiveflowers.service.TokenBlacklistService;
import com.example.backendfiveflowers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(7);
        tokenBlacklistService.blacklistToken(token); // Đưa token vào danh sách đen
        SecurityContextHolder.clearContext(); // Xóa ngữ cảnh bảo mật hiện tại
        return ResponseEntity.ok().build(); // Trả về phản hồi thành công
    }


    @PostMapping("/login")
    public UserRoles login(@RequestBody Users user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Users authenticatedUser = userService.getUser(user.getUserName());
        return new UserRoles(authenticatedUser.getUserName(), authenticatedUser.getEmail(), roles);
    }

    @PostMapping("/register")
    public Users registerUser(@RequestBody Users user) {
        return userService.saveUser(user);
    }

    @PostMapping("/register/admin")
    public Users registerAdmin(@RequestBody Users user) {
        Users savedUser = userService.saveUser(user);
        userService.addRoleToUser(savedUser.getUserName(), "ADMIN");
        return savedUser;
    }
}
