package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.OrderDetail;
import com.example.backendfiveflowers.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/order_details")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/add")
    public OrderDetail addOrderDetail(@RequestBody OrderDetail orderDetail) {
        return orderDetailService.addOrderDetail(orderDetail);
    }

    @PutMapping("/update")
    public OrderDetail updateOrderDetail(@RequestBody OrderDetail orderDetail) {
        return orderDetailService.updateOrderDetail(orderDetail);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrderDetail(@PathVariable Integer id) {
        orderDetailService.deleteOrderDetail(id);
    }

    @GetMapping("/get/{id}")
    public OrderDetail getOrderDetailById(@PathVariable Integer id) {
        return orderDetailService.getOrderDetailById(id).orElse(null);
    }

    @GetMapping("/all")
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailService.getAllOrderDetails();
    }
}
