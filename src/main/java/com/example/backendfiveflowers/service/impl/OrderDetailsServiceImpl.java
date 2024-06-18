package com.example.backendfiveflowers.service.impl;

import com.example.backendfiveflowers.entity.OrderDetails;
import com.example.backendfiveflowers.entity.OrderDetails.OrderDetailId;
import com.example.backendfiveflowers.exception.OrderDetailsNotFoundException;
import com.example.backendfiveflowers.repository.OrderDetailsRepository;
import com.example.backendfiveflowers.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    @Override
    public Optional<OrderDetails> getOrderDetailsById(OrderDetailId id) {
        return orderDetailsRepository.findById(id);
    }

    @Override
    public OrderDetails createOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    @Override
    public OrderDetails updateOrderDetails(OrderDetailId id, OrderDetails orderDetails) {
        if (!orderDetailsRepository.existsById(id)) {
            throw new OrderDetailsNotFoundException("OrderDetails with id " + id + " not found");
        }
        orderDetails.setId(id);
        return orderDetailsRepository.save(orderDetails);
    }

    @Override
    public void deleteOrderDetails(OrderDetailId id) {
        if (!orderDetailsRepository.existsById(id)) {
            throw new OrderDetailsNotFoundException("OrderDetails with id " + id + " not found");
        }
        orderDetailsRepository.deleteById(id);
    }
}
