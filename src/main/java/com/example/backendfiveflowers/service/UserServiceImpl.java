package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Role;
import com.example.backendfiveflowers.entity.User;
import com.example.backendfiveflowers.repository.RoleRepository;
import com.example.backendfiveflowers.repository.UserRepository;
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
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUserName(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
