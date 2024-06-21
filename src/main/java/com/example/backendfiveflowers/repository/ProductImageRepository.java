package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProduct(Product product);
}
