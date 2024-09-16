package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.ContactMessage;
import com.example.backendfiveflowers.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactMessageService {
    @Autowired
    private ContactMessageRepository repository;

    public ContactMessage saveMessage(ContactMessage message) {
        return repository.save(message);
    }
    public List<ContactMessage> getAllMessages() {
        return repository.findAll();
    }
}

