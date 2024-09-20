package com.example.backendfiveflowers.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(nullable = true) // Cho phép null
    private Double amount;
    private String category;
    private String note;

    @ManyToOne
    @JoinColumn(name = "hour_id")
    @JsonBackReference // Ngăn việc tuần tự hóa ngược từ Expense sang Hour
    private Hour hour;
}
