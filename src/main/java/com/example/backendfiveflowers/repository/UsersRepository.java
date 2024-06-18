package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUserName(String userName);
}
