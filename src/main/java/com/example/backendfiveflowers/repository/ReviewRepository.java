package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
