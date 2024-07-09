package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProduct(Product product);
    boolean existsByImageUrlAndProduct(String imageUrl, Product product);
    Optional<ProductImage> findByImageUrl(String imageUrl);


}
