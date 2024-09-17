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
        chatbotMessageService.saveBotResponse(botResponse);
        return ResponseEntity.ok("Phản hồi của chatbot đã được lưu!");
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatbotMessage>> getChatHistory() {
        List<ChatbotMessage> history = chatbotMessageService.getAllMessages();
        return ResponseEntity.ok(history);
    }
}
