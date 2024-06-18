package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.OrderDetails;
import com.example.backendfiveflowers.entity.OrderDetails.OrderDetailId;
import com.example.backendfiveflowers.exception.ResourceNotFoundException;
import com.example.backendfiveflowers.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orderdetails")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping
    public Page<OrderDetails> getAllOrderDetails(Pageable pageable) {
        return orderDetailsService.findAll(pageable);
    }

    @GetMapping("/{orderId}/{productId}")
    public Optional<OrderDetails> getOrderDetailById(@PathVariable Long orderId, @PathVariable Long productId) {
        OrderDetailId id = new OrderDetailId(orderId, productId);
        return orderDetailsService.findById(id);
    }

    @PostMapping
    public OrderDetails createOrderDetail(@RequestBody OrderDetails orderDetails) {
        return orderDetailsService.save(orderDetails);
    }

    @PutMapping("/{orderId}/{productId}")
    public OrderDetails updateOrderDetail(@PathVariable Long orderId, @PathVariable Long productId, @RequestBody OrderDetails orderDetailDetails) {
        OrderDetailId id = new OrderDetailId(orderId, productId);
        OrderDetails orderDetails = orderDetailsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("OrderDetail not found"));
        orderDetails.setOrder(orderDetailDetails.getOrder());
        orderDetails.setProduct(orderDetailDetails.getProduct());
        orderDetails.setQuantity(orderDetailDetails.getQuantity());
        orderDetails.setPrice(orderDetailDetails.getPrice());
        return orderDetailsService.save(orderDetails);
    }

    @DeleteMapping("/{orderId}/{productId}")
    public void deleteOrderDetail(@PathVariable Long orderId, @PathVariable Long productId) {
        OrderDetailId id = new OrderDetailId(orderId, productId);
        orderDetailsService.deleteById(id);
    }
}
