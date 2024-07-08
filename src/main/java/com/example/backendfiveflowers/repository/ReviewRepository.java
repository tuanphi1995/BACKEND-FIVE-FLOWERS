package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProduct_ProductId(Integer productId); // Thêm phương thức này
}
