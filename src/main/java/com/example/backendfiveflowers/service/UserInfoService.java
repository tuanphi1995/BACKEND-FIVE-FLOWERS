package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        // Kiểm tra tên người dùng trùng lặp (không phân biệt chữ hoa chữ thường)
        if (userInfoRepository.findByUserNameIgnoreCase(userInfo.getUserName()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setRoles("ROLE_USER");
        userInfoRepository.save(userInfo);
        return "User added successfully";
    }

    public UserInfo findByUserName(String userName) {
        return userInfoRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Page<UserInfo> getAllUsers(Pageable pageable) {
        return userInfoRepository.findAll(pageable);
    }

    public UserInfo getUserById(Integer id) {
        return userInfoRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    public UserInfo updateUser(UserInfo userInfo) {
        UserInfo existingUser = userInfoRepository.findById(userInfo.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userInfo.getId()));

        existingUser.setUserName(userInfo.getUserName());
        existingUser.setEmail(userInfo.getEmail());
        existingUser.setRoles(userInfo.getRoles());

        if (!passwordEncoder.matches(userInfo.getPassword(), existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        }

        return userInfoRepository.save(existingUser);
    }

    public void deleteUser(Integer id) {
        UserInfo existingUser = userInfoRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        userInfoRepository.delete(existingUser);
    }
}
