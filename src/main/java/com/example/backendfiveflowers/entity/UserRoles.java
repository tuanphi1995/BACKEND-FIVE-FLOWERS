package com.example.backendfiveflowers.entity;

import java.util.Set;

public class UserRoles {
    private String userName;
    private String email; // Thêm trường email
    private Set<String> roles;

    // Constructors, getters, and setters

    public UserRoles(String userName, String email, Set<String> roles) {
        this.userName = userName;
        this.email = email;
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
