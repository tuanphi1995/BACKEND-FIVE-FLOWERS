package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CartRepository extends JpaRepository<Cart, Long> {
    long countByAddToCartTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
