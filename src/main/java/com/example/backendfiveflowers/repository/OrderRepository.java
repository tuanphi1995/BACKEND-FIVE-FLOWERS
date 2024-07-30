package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Order> findByUserId(Integer userId);
}
