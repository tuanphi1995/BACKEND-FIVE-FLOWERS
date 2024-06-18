package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.OrderDetails;
import com.example.backendfiveflowers.entity.OrderDetails.OrderDetailId;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsService {
    List<OrderDetails> getAllOrderDetails();

    Optional<OrderDetails> getOrderDetailsById(OrderDetailId id);

    OrderDetails createOrderDetails(OrderDetails orderDetails);

    OrderDetails updateOrderDetails(OrderDetailId id, OrderDetails orderDetails);

    void deleteOrderDetails(OrderDetailId id);
}
