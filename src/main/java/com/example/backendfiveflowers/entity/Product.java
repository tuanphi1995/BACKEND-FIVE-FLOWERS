package com.example.backendfiveflowers.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Set;

@Entity
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String color;
    private Integer quantity;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @OneToMany(mappedBy = "product")
    private Set<ProductImage> productImages;

    // Getters and Setters
}
