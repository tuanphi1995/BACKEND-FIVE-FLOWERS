package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.ContactMessage;
import com.example.backendfiveflowers.model.EmailRequest;
import com.example.backendfiveflowers.service.ContactMessageService;
import com.example.backendfiveflowers.service.EmailContentBuilder;
import com.example.backendfiveflowers.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactMessageController {

    @Autowired
    private ContactMessageService service;

    @Autowired
    private EmailService emailService; // Thêm EmailService

    @Autowired
    private EmailContentBuilder emailContentBuilder; // Thêm EmailContentBuilder để tạo nội dung email

    @PostMapping("/submit")
    public ResponseEntity<String> submitContactForm(@RequestBody ContactMessage message) {
        // Lưu thông tin liên hệ vào cơ sở dữ liệu
        service.saveMessage(message);

        // Xây dựng nội dung email xác nhận
        String emailBody = emailContentBuilder.buildContactConfirmationEmail(message.getName());

        // Gửi email xác nhận cho người dùng với địa chỉ email họ đã nhập
        EmailRequest emailRequest = new EmailRequest(message.getEmail(), "Thank you for contacting us", emailBody);

        emailService.sendHtmlEmail(emailRequest);  // Gửi email xác nhận

        // Phản hồi lại rằng tin nhắn đã được gửi thành công
        return new ResponseEntity<>("Tin nhắn đã được gửi thành công!", HttpStatus.OK);
    }
    @GetMapping("/allcontact")
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        List<ContactMessage> messages = service.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
