package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.AuthRequest;
import com.example.backendfiveflowers.entity.AuthResponse;
import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.service.JwtService;
import com.example.backendfiveflowers.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Spring Boot Security JWT!";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );

            if (authentication.isAuthenticated()) {
                UserInfo userInfo = userInfoService.findByUserName(authRequest.getUserName());
                String token = jwtService.generateToken(userInfo.getUserName());
                return ResponseEntity.ok(new AuthResponse(token, "Success"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Add this line to see the exception details
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
