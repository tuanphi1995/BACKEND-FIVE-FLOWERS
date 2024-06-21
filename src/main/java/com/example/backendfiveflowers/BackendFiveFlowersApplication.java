package com.example.backendfiveflowers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackendFiveFlowersApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendFiveFlowersApplication.class, args);
    }
}
