package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
