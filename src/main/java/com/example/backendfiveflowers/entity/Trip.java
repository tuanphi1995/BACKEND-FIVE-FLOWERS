package com.example.backendfiveflowers.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tripName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String startLocation;
    private String endLocation;

    // Thêm cột lưu trữ khoảng cách dưới dạng text
    private String distance; // Lưu khoảng cách dưới dạng văn bản

    private Double totalBudget;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserInfo user;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Itinerary> itineraries;
}
