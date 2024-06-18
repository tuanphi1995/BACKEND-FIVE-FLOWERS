package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
