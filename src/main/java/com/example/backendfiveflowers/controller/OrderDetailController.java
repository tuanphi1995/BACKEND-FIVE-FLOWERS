package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.OrderDetail;
import com.example.backendfiveflowers.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/order-details")
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
    public OrderDetail updateOrderDetail(@PathVariable Integer id, @RequestBody OrderDetail orderDetail) {
        if (!id.equals(orderDetail.getOrderDetailId())) {
            throw new IllegalArgumentException("Order Detail ID does not match");
        }
        return orderDetailService.updateOrderDetail(orderDetail);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
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
    public Page<OrderDetail> getAllOrderDetails(Pageable pageable) {
        return orderDetailService.getAllOrderDetails(pageable);
    }

    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<OrderDetail> getOrderDetailsByProductId(@PathVariable Integer productId) {
        return orderDetailService.getOrderDetailsByProductId(productId);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public OrderDetail updateOrderDetailStatus(@PathVariable Integer id, @RequestBody Map<String, String> statusUpdate) {
        String status = statusUpdate.get("status");
        return orderDetailService.updateOrderDetailStatus(id, status);
    }
}
