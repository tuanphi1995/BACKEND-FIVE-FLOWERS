package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Bike;
import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.repository.BikeRepository;
import com.example.backendfiveflowers.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BikeRepository bikeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByUserName(username);
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }

    public String addUser(UserInfo userInfo) {
        if (userInfoRepository.findByUserNameIgnoreCase(userInfo.getUserName()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setRoles("ROLE_USER");
        userInfoRepository.save(userInfo);
        return "User added successfully";
    }

    public String addAdmin(UserInfo userInfo) {
        if (userInfoRepository.findByUserNameIgnoreCase(userInfo.getUserName()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setRoles("ROLE_ADMIN");
        userInfoRepository.save(userInfo);
        return "Admin added successfully";
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

    public Page<UserInfo> getAllAdmins(Pageable pageable) {
        return userInfoRepository.findAllAdmins("ROLE_ADMIN", pageable);
    }

    public int getNewUsersCount(LocalDate date, String role) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return userInfoRepository.countByCreatedAtBetweenAndRolesContaining(startOfDay, endOfDay, role);
    }

    public UserInfo getCurrentUser(String userName) {
        return userInfoRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    public List<Bike> getUserBikes(Integer userId) {
        return bikeRepository.findByUserId(userId);
    }

    public ResponseEntity<UserInfo> putUser(Integer id, UserInfo userInfo) {
        Optional<UserInfo> existingUserInfo = userInfoRepository.findById(id);
        if (!existingUserInfo.isPresent()) {
            throw new IllegalArgumentException("ID not found");
        }

        UserInfo updatedUserInfo = existingUserInfo.get();
        updatedUserInfo.setUserName(userInfo.getUserName());
        updatedUserInfo.setPassword(userInfo.getPassword());
        updatedUserInfo.setEmail(userInfo.getEmail());
        updatedUserInfo.setImg(userInfo.getImg());
        updatedUserInfo.setRoles(userInfo.getRoles());

        // Save the updated user info object to the database
        userInfoRepository.save(updatedUserInfo);

        return ResponseEntity.ok(updatedUserInfo);
    }
}