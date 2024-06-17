package com.example.backendfiveflowers.entity;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name", nullable = false, length = 255)
    private String categoryName;

    @OneToMany(mappedBy = "category")
        private Set<Products> products;

    // Getters and Setters
}
