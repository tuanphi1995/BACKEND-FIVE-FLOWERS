package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.ChatbotMessage;
import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.repository.ChatbotMessageRepository;
import com.example.backendfiveflowers.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChatbotMessageService {

    @Autowired
    private ChatbotMessageRepository chatbotMessageRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    // Hàm lưu phản hồi của chatbot vào cơ sở dữ liệu với thông tin người dùng
    public void saveBotResponse(String botResponse, String startLocation, String endLocation, Integer userId) {
        ChatbotMessage message = new ChatbotMessage();
        message.setBotResponse(botResponse);
        message.setStartLocation(startLocation);
        message.setEndLocation(endLocation);
        message.setTimestamp(new Date());

        // Lấy thông tin người dùng từ ID
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Gắn người dùng vào tin nhắn chatbot
        message.setUserInfo(user);

        // Lưu tin nhắn
        chatbotMessageRepository.save(message);
    }

    // Hàm lấy tin nhắn theo `userId`
    public List<ChatbotMessage> getMessagesByUserId(Integer userId) {
        return chatbotMessageRepository.findByUserInfoId(userId);
    }
    public Optional<ChatbotMessage> getMessagesByMessageId(Long id) {
        return chatbotMessageRepository.findById(id); // Trả về Optional<ChatbotMessage>
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

    // Hàm xóa tin nhắn dựa trên ID
    public void deleteChatById(Long id) {
        chatbotMessageRepository.deleteById(id);
    }
    public List<ChatbotMessage> getMessagesByUserIdAndMessageId(Integer userId, Long id) {
        return chatbotMessageRepository.findByUserInfoIdAndId(userId, id);
    }

    // Hàm cập nhật nội dung phản hồi của chatbot trong lịch sử cuộc trò chuyện
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
