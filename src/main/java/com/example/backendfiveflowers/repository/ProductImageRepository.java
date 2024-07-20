package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProduct(Product product);
    @Query("SELECT CASE WHEN COUNT(pi) > 0 THEN true ELSE false END FROM ProductImage pi WHERE pi.imageUrl = :imageUrl AND pi.product = :product")
    boolean existsByImageUrlAndProduct(@Param("imageUrl") String imageUrl, @Param("product") Product product);
    Optional<ProductImage> findByImageUrl(String imageUrl);


}
