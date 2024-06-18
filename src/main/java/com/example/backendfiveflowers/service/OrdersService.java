package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrdersService {
    List<Orders> findAll();

    Page<Orders> findAll(Pageable pageable); // Thêm phương thức này
    Optional<Orders> findById(Long id);
    Orders save(Orders orders);
    void deleteById(Long id);
}
