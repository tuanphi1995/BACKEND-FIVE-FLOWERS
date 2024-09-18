package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.ChatbotMessage;
import com.example.backendfiveflowers.service.ChatbotMessageService;
import com.example.backendfiveflowers.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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


    @PostMapping("/save")
    public ResponseEntity<?> saveBotResponse(@RequestBody Map<String, String> request) {
        String botResponse = request.get("botResponse");
        String startLocation = request.get("startLocation");
        String endLocation = request.get("endLocation");

        // Lưu phản hồi chatbot cùng với startLocation và endLocation
        chatbotMessageService.saveBotResponse(botResponse, startLocation, endLocation);
        return ResponseEntity.ok("Phản hồi của chatbot đã được lưu cùng với thông tin điểm đi và đến!");
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatbotMessage>> getChatHistory() {
        List<ChatbotMessage> history = chatbotMessageService.getAllMessages();
        return ResponseEntity.ok(history);
    }
    @GetMapping("/history/{id}")
    public ResponseEntity<List<ChatbotMessage>> getChatHistory(@PathVariable Long id) {
        List<ChatbotMessage> history = chatbotMessageService.getMessagesById(id);
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
}