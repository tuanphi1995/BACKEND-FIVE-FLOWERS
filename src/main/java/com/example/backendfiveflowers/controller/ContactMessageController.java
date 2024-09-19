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
    private EmailService emailService;

    @Autowired
    private EmailContentBuilder emailContentBuilder;

    // API để lấy thông tin ContactMessage theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactMessage> getContactMessageById(@PathVariable Long id) {
        ContactMessage contactMessage = service.getMessageById(id);
        if (contactMessage != null) {
            return new ResponseEntity<>(contactMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitContactForm(@RequestBody ContactMessage message) {
        service.saveMessage(message);
        String emailBody = emailContentBuilder.buildContactConfirmationEmail(message.getName());
        EmailRequest emailRequest = new EmailRequest(message.getEmail(), "Thank you for contacting us", emailBody);
        emailService.sendHtmlEmail(emailRequest);
        return new ResponseEntity<>("The message has been sent successfully!", HttpStatus.OK);
    }

    @GetMapping("/allcontact")
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        List<ContactMessage> messages = service.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}

