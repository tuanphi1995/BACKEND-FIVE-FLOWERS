package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.ChatbotMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ChatbotMessageRepository extends JpaRepository<ChatbotMessage, Long> {
    List<ChatbotMessage> findByUserInfoId(Integer userId); // Tìm các tin nhắn theo ID người dùng
}
