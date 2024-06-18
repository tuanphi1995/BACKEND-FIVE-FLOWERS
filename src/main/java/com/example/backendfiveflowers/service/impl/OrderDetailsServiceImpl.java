package com.example.backendfiveflowers.service.impl;

import com.example.backendfiveflowers.entity.OrderDetails;
import com.example.backendfiveflowers.entity.OrderDetails.OrderDetailId;
import com.example.backendfiveflowers.repository.OrderDetailsRepository;
import com.example.backendfiveflowers.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public List<OrderDetails> findAll() {
        return orderDetailsRepository.findAll();
    }

    @Override
    public Page<OrderDetails> findAll(Pageable pageable) {
        return orderDetailsRepository.findAll(pageable);
    }

    @Override
    public Optional<OrderDetails> findById(OrderDetailId id) {
        return orderDetailsRepository.findById(id);
    }

    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    @Override
    public void deleteById(OrderDetailId id) {
        orderDetailsRepository.deleteById(id);
    }
}
