package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {
}
