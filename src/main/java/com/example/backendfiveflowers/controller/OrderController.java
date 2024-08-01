package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Order;
import com.example.backendfiveflowers.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
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
    public Order updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public void deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public Order getOrderById(@PathVariable Integer id) {
        Optional<Order> orderOptional = orderService.getOrderById(id);
        return orderOptional.orElse(null);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderService.getAllOrders(pageable);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<Order> getOrdersByUserId(@PathVariable Integer userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Order updateOrderStatus(@PathVariable Integer id, @RequestBody Map<String, String> statusUpdate) {
        String status = statusUpdate.get("status");
        return orderService.updateOrderStatus(id, status);
    }

    @GetMapping("/top-selling-products-today")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<Map<String, Object>> getTopSellingProductsToday() {
        return orderService.getTopSellingProductsToday();
    }

    @GetMapping("/daily-sales-totals")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public Map<String, Double> getDailySalesTotals(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return orderService.getDailySalesTotals(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @GetMapping("/top-selling-products")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<Map<String, Object>> getTopSellingProducts(@RequestParam("date") String date) {
        return orderService.getTopSellingProducts(LocalDate.parse(date));
    }

    @GetMapping("/new-orders")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public Map<String, Integer> getNewOrdersCount(@RequestParam("date") String date) {
        int newOrdersCount = orderService.getNewOrdersCount(LocalDate.parse(date));
        return Collections.singletonMap("newOrdersCount", newOrdersCount);
    }

    @GetMapping("/pending-orders")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public Map<String, Integer> getPendingOrdersCount(@RequestParam("date") String date) {
        int pendingOrdersCount = orderService.getPendingOrdersCount(LocalDate.parse(date));
        return Collections.singletonMap("pendingOrdersCount", pendingOrdersCount);
    }


}
