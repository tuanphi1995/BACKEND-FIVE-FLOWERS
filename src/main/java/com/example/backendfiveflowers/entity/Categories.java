package com.example.backendfiveflowers.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<Products> products;

    // Getters and Setters
}

