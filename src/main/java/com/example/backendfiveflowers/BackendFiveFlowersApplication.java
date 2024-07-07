package com.example.backendfiveflowers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling // Thêm annotation này để bật tính năng Scheduling
public class BackendFiveFlowersApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendFiveFlowersApplication.class, args);
    }
}
