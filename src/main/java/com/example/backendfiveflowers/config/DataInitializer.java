package com.example.backendfiveflowers.config;

import com.example.backendfiveflowers.entity.Role;
import com.example.backendfiveflowers.entity.User;
import com.example.backendfiveflowers.repository.RoleRepository;
import com.example.backendfiveflowers.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (roleRepository.findByName("ADMIN") == null) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByName("CUSTOMER") == null) {
            Role customerRole = new Role();
            customerRole.setName("CUSTOMER");
            roleRepository.save(customerRole);
        }

        if (userRepository.findByUserName("admin") == null) {
            User adminUser = new User();
            adminUser.setUserName("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.getRoles().add(roleRepository.findByName("ADMIN"));
            userRepository.save(adminUser);
        }

        if (userRepository.findByUserName("customer") == null) {
            User customerUser = new User();
            customerUser.setUserName("customer");
            customerUser.setPassword(passwordEncoder.encode("customer"));
            customerUser.getRoles().add(roleRepository.findByName("CUSTOMER"));
            userRepository.save(customerUser);
        }
    }
}
