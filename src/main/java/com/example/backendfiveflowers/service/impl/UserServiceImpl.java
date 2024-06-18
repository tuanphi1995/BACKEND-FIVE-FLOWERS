package com.example.backendfiveflowers.service.impl;

import com.example.backendfiveflowers.entity.Roles;
import com.example.backendfiveflowers.entity.Users;
import com.example.backendfiveflowers.repository.RoleRepository;
import com.example.backendfiveflowers.repository.UserRepository;
import com.example.backendfiveflowers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users saveUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        Users user = userRepository.findByUserName(username);
        Roles role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public Users getUser(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public List<Users> getUsers() {
        return userRepository.findAll();
    }
}
