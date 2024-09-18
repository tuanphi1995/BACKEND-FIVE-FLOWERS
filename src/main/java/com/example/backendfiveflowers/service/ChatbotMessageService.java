package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.ChatbotMessage;
import com.example.backendfiveflowers.repository.ChatbotMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChatbotMessageService {
    @Autowired
    private ChatbotMessageRepository chatbotMessageRepository;

    // Hàm lưu phản hồi của chatbot vào cơ sở dữ liệu
    public void saveBotResponse(String botResponse, String startLocation, String endLocation) {
        ChatbotMessage message = new ChatbotMessage();
        message.setBotResponse(botResponse);
        message.setStartLocation(startLocation);  // Lưu địa chỉ bắt đầu
        message.setEndLocation(endLocation);      // Lưu địa chỉ kết thúc
        message.setTimestamp(new Date());
        chatbotMessageRepository.save(message);
    }

    // Hàm lấy tất cả các tin nhắn đã lưu theo id của cuộc trò chuyện
    public List<ChatbotMessage> getMessagesById(Long id) {
        return chatbotMessageRepository.findAllById(id);  // Sử dụng phương thức tùy chỉnh
    }

    // Hàm lấy tất cả các tin nhắn đã lưu
    public List<ChatbotMessage> getAllMessages() {
        return chatbotMessageRepository.findAll();
    }

    // Hàm cập nhật tên của cuộc trò chuyện (lịch trình)
    public ChatbotMessage updateConversationName(Long id, String newName) {
        Optional<ChatbotMessage> optionalMessage = chatbotMessageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            ChatbotMessage message = optionalMessage.get();
            message.setName(newName); // Cập nhật tên mới
            chatbotMessageRepository.save(message); // Lưu thay đổi vào cơ sở dữ liệu
            return message;
        } else {
            throw new RuntimeException("Cuộc trò chuyện không tồn tại với id: " + id);
        }
    }

    public void deleteChatById(Long id) {
        chatbotMessageRepository.deleteById(id);
    }

    public ChatbotMessage updateChatHistory(Long id, String newBotResponse) {
        Optional<ChatbotMessage> optionalMessage = chatbotMessageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            ChatbotMessage message = optionalMessage.get();
            message.setBotResponse(newBotResponse); // Cập nhật nội dung mới
            chatbotMessageRepository.save(message); // Lưu thay đổi vào cơ sở dữ liệu
            return message;
        } else {
            throw new RuntimeException("Cuộc trò chuyện không tồn tại với id: " + id);
        }
    }
}
