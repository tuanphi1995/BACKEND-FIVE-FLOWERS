package com.example.backendfiveflowers.config;

import com.example.backendfiveflowers.entity.Roles;
import com.example.backendfiveflowers.entity.Users;
import com.example.backendfiveflowers.repository.RolesRepository;
import com.example.backendfiveflowers.repository.UsersRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private RolesRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (roleRepository.findByName("ADMIN") == null) {
            Roles adminRole = new Roles();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByName("CUSTOMER") == null) {
            Roles customerRole = new Roles();
            customerRole.setName("CUSTOMER");
            roleRepository.save(customerRole);
        }

        if (userRepository.findByUserName("admin") == null) {
            Users adminUser = new Users();
            adminUser.setUserName("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setEmail("admin@example.com"); // Thêm email cho admin
            adminUser.getRoles().add(roleRepository.findByName("ADMIN"));
            userRepository.save(adminUser);
        }

        if (userRepository.findByUserName("customer") == null) {
            Users customerUser = new Users();
            customerUser.setUserName("customer");
            customerUser.setPassword(passwordEncoder.encode("customer"));
            customerUser.setEmail("customer@example.com"); // Thêm email cho customer
            customerUser.getRoles().add(roleRepository.findByName("CUSTOMER"));
            userRepository.save(customerUser);
        }
    }
}
