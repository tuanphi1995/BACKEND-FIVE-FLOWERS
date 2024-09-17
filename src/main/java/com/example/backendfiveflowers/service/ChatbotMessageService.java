package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.ChatbotMessage;
import com.example.backendfiveflowers.repository.ChatbotMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatbotMessageService {
    @Autowired
    private ChatbotMessageRepository chatbotMessageRepository;

    // Hàm lưu phản hồi của chatbot vào cơ sở dữ liệu
    public void saveBotResponse(String botResponse) {
        ChatbotMessage message = new ChatbotMessage();
        message.setBotResponse(botResponse);
        message.setTimestamp(new Date());
        chatbotMessageRepository.save(message);
    }

    // Hàm lấy tất cả các tin nhắn đã lưu
    public List<ChatbotMessage> getAllMessages() {
        return chatbotMessageRepository.findAll();
    }
}
