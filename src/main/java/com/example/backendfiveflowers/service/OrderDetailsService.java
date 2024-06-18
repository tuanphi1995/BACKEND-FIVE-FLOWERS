package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.OrderDetails;
import com.example.backendfiveflowers.entity.OrderDetails.OrderDetailId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsService {
    List<OrderDetails> findAll();

    Page<OrderDetails> findAll(Pageable pageable);

    Optional<OrderDetails> findById(OrderDetailId id);

    OrderDetails save(OrderDetails orderDetails);

    void deleteById(OrderDetailId id);
}
