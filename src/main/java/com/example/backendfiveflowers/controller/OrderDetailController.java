package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.OrderDetail;
import com.example.backendfiveflowers.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order_details")

public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public OrderDetail addOrderDetail(@RequestBody OrderDetail orderDetail) {
        return orderDetailService.addOrderDetail(orderDetail);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public OrderDetail updateOrderDetail(@RequestBody OrderDetail orderDetail) {
        return orderDetailService.updateOrderDetail(orderDetail);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrderDetail(@PathVariable Integer id) {
        orderDetailService.deleteOrderDetail(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public OrderDetail getOrderDetailById(@PathVariable Integer id) {
        return orderDetailService.getOrderDetailById(id).orElse(null);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailService.getAllOrderDetails();
    }
}
