package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.OrderDetail;
import com.example.backendfiveflowers.entity.Order;
import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.repository.OrderDetailRepository;
import com.example.backendfiveflowers.repository.OrderRepository;
import com.example.backendfiveflowers.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public OrderDetail addOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetail updateOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public void deleteOrderDetail(Integer id) {
        Optional<OrderDetail> orderDetailOptional = orderDetailRepository.findById(id);
        if (orderDetailOptional.isPresent()) {
            OrderDetail orderDetail = orderDetailOptional.get();
            String username = getCurrentUsername();
            if (isAdmin() || orderDetail.getOrders().stream().anyMatch(order -> order.getUser().getUserName().equals(username))) {
                orderDetailRepository.deleteById(id);
            } else {
                throw new RuntimeException("You do not have permission to delete this order detail");
            }
        } else {
            throw new RuntimeException("Order detail not found");
        }
    }

    public Optional<OrderDetail> getOrderDetailById(Integer id) {
        return orderDetailRepository.findById(id);
    }

    public Page<OrderDetail> getAllOrderDetails(Pageable pageable) {
        return orderDetailRepository.findAll(pageable);
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    private boolean isAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
    }
}
