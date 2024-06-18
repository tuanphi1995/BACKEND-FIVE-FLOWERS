package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Orders;

import java.util.List;
import java.util.Optional;

public interface OrdersService {
    List<Orders> findAll();

    Optional<Orders> findById(Long id);

    Orders save(Orders orders);

    void deleteById(Long id);
}
