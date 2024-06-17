package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.User;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
