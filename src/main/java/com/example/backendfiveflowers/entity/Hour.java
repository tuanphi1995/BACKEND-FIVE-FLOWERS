package com.example.backendfiveflowers.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Getter
@Setter
@Entity
public class Hour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime time; // Sử dụng LocalTime

    @ManyToOne
    @JoinColumn(name = "day_id", nullable = true)
    @JsonBackReference
    private Day day;

    @OneToMany(mappedBy = "hour", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Expense> expenses;
}
