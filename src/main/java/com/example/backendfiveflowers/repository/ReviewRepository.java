package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProduct_ProductId(Integer productId);
    @Query("SELECT p FROM Product p WHERE p.productId IN (SELECT DISTINCT r.product.productId FROM Review r)")
    Page<Product> findReviewedProducts(Pageable pageable);// Thêm phương thức này
}
