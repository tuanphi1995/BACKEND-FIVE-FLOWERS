package com.example.backendfiveflowers.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount; // Số tiền
    private String category; // Danh mục
    private String date; // Ngày tháng
    private String note; // Ghi chú
}
