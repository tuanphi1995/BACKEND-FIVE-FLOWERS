package com.example.backendfiveflowers.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "brand_name", nullable = false, length = 255)
    private String brandName;

    @OneToMany(mappedBy = "brand")
    private Set<Products> products;

    // Getters and Setters
}
