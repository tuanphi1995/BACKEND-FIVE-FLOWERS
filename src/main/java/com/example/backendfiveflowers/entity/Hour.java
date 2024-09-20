package com.example.backendfiveflowers.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Hour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "day_id", nullable = true) // Để nullable = true nếu có thể null
    @JsonBackReference
    private Day day;

    @OneToMany(mappedBy = "hour", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Expense> expenses; // Có thể là null
}
