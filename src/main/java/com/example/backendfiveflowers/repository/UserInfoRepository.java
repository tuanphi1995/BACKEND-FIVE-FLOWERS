package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findById(int id);
    Optional<UserInfo> findByUserName(String userName); // Correct method name matching the field
}
