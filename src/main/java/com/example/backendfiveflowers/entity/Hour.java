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
    @JoinColumn(name = "day_id")
    @JsonBackReference // Ngăn việc tuần tự hóa ngược từ Hour sang Day
    private Day day;

    @OneToMany(mappedBy = "hour", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Quản lý việc tuần tự hóa từ Hour sang Description
    private List<Description> descriptions;

    @OneToMany(mappedBy = "hour", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Quản lý việc tuần tự hóa từ Hour sang Expense
    private List<Expense> expenses;
}
