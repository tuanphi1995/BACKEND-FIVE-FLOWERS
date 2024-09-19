package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.AuthRequest;
import com.example.backendfiveflowers.entity.AuthResponse;
import com.example.backendfiveflowers.entity.Bike;
import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.repository.BikeRepository;
import com.example.backendfiveflowers.service.JwtService;
import com.example.backendfiveflowers.service.UserInfoService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BikeRepository bikeRepository;



    @PostMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/addAdmin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addAdmin(@RequestBody UserInfo userInfo) {
        return userInfoService.addAdmin(userInfo);
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

    @GetMapping("/new-users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Integer>> getNewUsersCount(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        int newUsersCount = userInfoService.getNewUsersCount(date, "ROLE_USER");
        return new ResponseEntity<>(Collections.singletonMap("newUsersCount", newUsersCount), HttpStatus.OK);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserInfo> getCurrentUser(Principal principal) {
        UserInfo currentUser = userInfoService.getCurrentUser(principal.getName());
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/bikes")
    public ResponseEntity<List<Bike>> getUserBikes(Principal principal) {
        if (principal == null) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        UserInfo user = userInfoService.getCurrentUser(principal.getName());

        if (user == null) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<Bike> bikes = bikeRepository.findByUserId(user.getId());
        return ResponseEntity.ok(bikes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserInfo> put (@PathVariable Integer id, @RequestBody UserInfo userInfo){
        return userInfoService.putUser(id, userInfo);
    }
}