package com.example.backendfiveflowers.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class ChatbotMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob  // Đánh dấu rằng trường này có thể chứa dữ liệu lớn
    private String botResponse; // Phản hồi từ chatbot

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp; // Thời gian phản hồi
}
