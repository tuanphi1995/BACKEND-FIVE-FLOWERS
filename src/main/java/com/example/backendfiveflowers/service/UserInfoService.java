package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByUserName(username);
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
//        userInfo.setRoles("ROLE_USER");
        userInfoRepository.save(userInfo);
        return "User added successfully";
    }

    public List<UserInfo> getAllUser() {
        return userInfoRepository.findAll();
    }

    public UserInfo getUserById(Integer id) {
        return userInfoRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }
}
