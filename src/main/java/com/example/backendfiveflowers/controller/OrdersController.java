package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Orders;
import com.example.backendfiveflowers.exception.ResourceNotFoundException;
import com.example.backendfiveflowers.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping
    public Page<Orders> getAllOrders(Pageable pageable) {
        return ordersService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Orders> getOrderById(@PathVariable Long id) {
        return ordersService.findById(id);
    }

    @PostMapping
    public Orders createOrder(@RequestBody Orders orders) {
        return ordersService.save(orders);
    }

    @PutMapping("/{id}")
    public Orders updateOrder(@PathVariable Long id, @RequestBody Orders orderDetails) {
        Orders orders = ordersService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        orders.setUser(orderDetails.getUser());
        orders.setOrderDate(orderDetails.getOrderDate());
        orders.setStatus(orderDetails.getStatus());
        orders.setTotalAmount(orderDetails.getTotalAmount());
        orders.setShippingStatus(orderDetails.getShippingStatus());
        orders.setTrackingNumber(orderDetails.getTrackingNumber());
        return ordersService.save(orders);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        ordersService.deleteById(id);
    }
}
