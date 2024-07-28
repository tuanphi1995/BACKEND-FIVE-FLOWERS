package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findById(int id);
    Optional<UserInfo> findByUserName(String userName);
    Optional<UserInfo> findByUserNameIgnoreCase(String userName);

    @Query("SELECT u FROM UserInfo u WHERE u.roles LIKE %?1%")
    Page<UserInfo> findAllAdmins(String role, Pageable pageable);
}
