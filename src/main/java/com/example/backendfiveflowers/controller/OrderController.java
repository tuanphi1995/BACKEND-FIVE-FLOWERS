package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Order;
import com.example.backendfiveflowers.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public Order addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public Order updateOrder(@RequestBody Order order) {
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public void deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public Order getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id).orElse(null);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderService.getAllOrders(pageable);
    }
}
