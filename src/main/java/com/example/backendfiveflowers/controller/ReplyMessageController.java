package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.ContactMessage;
import com.example.backendfiveflowers.service.ContactMessageService;
import com.example.backendfiveflowers.service.ReplyMessageService;
import com.example.backendfiveflowers.model.EmailRequest;
import com.example.backendfiveflowers.service.EmailContentBuilder;
import com.example.backendfiveflowers.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reply")
public class ReplyMessageController {

    @Autowired
    private ContactMessageService service;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailContentBuilder emailContentBuilder;

    @PostMapping("/send")
    public ResponseEntity<String> replyToCustomer(@RequestParam Long contactId, @RequestParam String message) {
        // Lấy thông tin liên hệ theo contactId
        ContactMessage contactMessage = service.getMessageById(contactId);
        if (contactMessage == null) {
            return new ResponseEntity<>("Contact message not found", HttpStatus.NOT_FOUND);
        }

        String contactName = contactMessage.getName();
        String contactEmail = contactMessage.getEmail();

        // Tạo nội dung email phản hồi động
        String emailBody = emailContentBuilder.buildDynamicResponseEmail(contactName, message);

        // Gửi email phản hồi
        emailService.sendHtmlEmail(new EmailRequest(contactEmail, "Your Inquiry Response", emailBody));

        return new ResponseEntity<>("Response sent successfully!", HttpStatus.OK);
    }
}

