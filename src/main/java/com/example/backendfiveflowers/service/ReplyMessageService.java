package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.ReplyMessage;
import com.example.backendfiveflowers.repository.ReplyMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor  // Lombok sẽ tạo constructor tự động cho các thuộc tính @Autowired
public class ReplyMessageService {

    private final ReplyMessageRepository repository;

    public ReplyMessage saveReplyMessage(String email, String message) {
        ReplyMessage replyMessage = new ReplyMessage(email, message);
        return repository.save(replyMessage);  // Lưu phản hồi vào cơ sở dữ liệu
    }
}
