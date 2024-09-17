package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.ChatbotMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatbotMessageRepository extends JpaRepository<ChatbotMessage, Long> {
}
