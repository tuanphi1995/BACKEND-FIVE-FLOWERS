package com.example.backendfiveflowers.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
class Brands {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    private String name;
    private String description;

    @OneToMany(mappedBy = "brand")
    private Set<Products> products;

    // Getters and Setters
}