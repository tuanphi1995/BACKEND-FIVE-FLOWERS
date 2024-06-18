package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
