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

    @Lob
    private String botResponse; // Phản hồi từ chatbot

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp; // Thời gian phản hồi

    private String name; // Tên của lịch trình, ví dụ: "Lịch trình 1"

    private String startLocation; // Điểm bắt đầu

    private String endLocation; // Điểm kết thúc
}
