package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.ChatbotMessage;
import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.service.ChatbotMessageService;
import com.example.backendfiveflowers.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bot")
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value("${open.api.url}")
    private String apiURL;

    @Value("${flask.base.url}")
    private String flaskBaseUrl;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ChatbotMessageService chatbotMessageService;

    @Autowired
    private UserInfoService userInfoService;


    @PostMapping("/learn")
    public String learn(@RequestBody Map<String, Object> text) {
        String url = flaskBaseUrl + "/learn";
        ResponseEntity<String> response = restTemplate.postForEntity(url, text, String.class);
        return response.getBody();
    }

    @PostMapping("/ask")
    public String ask(@RequestBody Map<String, Object> question) {
        String url = flaskBaseUrl + "/ask";
        ResponseEntity<String> response = restTemplate.postForEntity(url, question, String.class);
        return response.getBody();
    }


    // Hàm để lưu phản hồi của chatbot
    @PostMapping("/save")
    public ResponseEntity<?> saveBotResponse(@RequestBody Map<String, String> request) {
        String botResponse = request.get("botResponse");
        String startLocation = request.get("startLocation");
        String endLocation = request.get("endLocation");

        // Lấy userId từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserInfo user = userInfoService.findByUserName(userName);
        Integer userId = user.getId();

        // Lưu phản hồi chatbot cùng với thông tin người dùng
        chatbotMessageService.saveBotResponse(botResponse, startLocation, endLocation, userId);
        return ResponseEntity.ok("Chatbot response saved with user information!");
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatbotMessage>> getChatHistory() {
        List<ChatbotMessage> history = chatbotMessageService.getAllMessages();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<ChatbotMessage>> getChatHistoryByUserId(@PathVariable Integer userId) {
        System.out.println("Fetching chat history for userId: " + userId);
        List<ChatbotMessage> history = chatbotMessageService.getMessagesByUserId(userId);
        System.out.println("Chat history for userId " + userId + ": " + history);
        return ResponseEntity.ok(history);
    }



    @PutMapping("/updateName/{id}")
    public ResponseEntity<?> updateConversationName(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newName = request.get("name");

        try {
            ChatbotMessage updatedMessage = chatbotMessageService.updateConversationName(id, newName);
            return ResponseEntity.ok("Tên cuộc trò chuyện đã được cập nhật!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteChatHistory(@PathVariable Long id) {
        try {
            chatbotMessageService.deleteChatById(id);
            return ResponseEntity.ok("Đã xóa lịch trình thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy lịch trình");
        }
    }
    @PutMapping("/updateHistory/{id}")
    public ResponseEntity<?> updateChatHistory(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newBotResponse = request.get("botResponse");

        try {
            ChatbotMessage updatedMessage = chatbotMessageService.updateChatHistory(id, newBotResponse);
            return ResponseEntity.ok("Lịch sử cuộc trò chuyện đã được cập nhật!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    @GetMapping("/history/chat/{chatId}")
    public ResponseEntity<?> getChatHistoryByChatId(@PathVariable Long chatId) {
        System.out.println("Fetching chat history for chatId: " + chatId);
        Optional<ChatbotMessage> chatHistory = chatbotMessageService.getMessagesByMessageId(chatId); // Gọi service
        if (chatHistory.isPresent()) {
            return ResponseEntity.ok(chatHistory.get()); // Trả về đối tượng nếu tìm thấy
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy cuộc trò chuyện với chatId: " + chatId);
        }
    }

}