package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImages, Long> {
}
