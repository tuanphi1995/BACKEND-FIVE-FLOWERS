package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.OrderDetails;
import com.example.backendfiveflowers.entity.OrderDetails.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetailId> {
}
