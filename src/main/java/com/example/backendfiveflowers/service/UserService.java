package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Users;
import java.util.List;

public interface UserService {
    Users saveUser(Users user);
    void addRoleToUser(String username, String roleName);
    Users getUser(String username);
    List<Users> getUsers();
}
