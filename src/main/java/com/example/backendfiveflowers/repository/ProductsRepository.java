package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {
}
