package com.example.backendfiveflowers.entity;

import java.util.Set;

public class AuthResponse {
    private String userName;
    private Set<String> roles;

    // Constructors, getters, and setters

    public AuthResponse(String userName, Set<String> roles) {
        this.userName = userName;
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}