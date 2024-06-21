package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Order;
import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.repository.OrderRepository;
import com.example.backendfiveflowers.repository.UserInfoRepository;
import com.example.backendfiveflowers.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order addOrder(Order order) {
        String username = getCurrentUsername();
        Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserName(username);
        if (userInfoOptional.isPresent()) {
            order.setUser(userInfoOptional.get());

            Optional<Product> productOptional = productRepository.findById(order.getProduct().getProductId());
            if (productOptional.isPresent()) {
                order.setProduct(productOptional.get());
                return orderRepository.save(order);
            } else {
                throw new RuntimeException("Product not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Order updateOrder(Order order) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(order.getUser().getId());
        if (userInfoOptional.isPresent()) {
            order.setUser(userInfoOptional.get());

            Optional<Product> productOptional = productRepository.findById(order.getProduct().getProductId());
            if (productOptional.isPresent()) {
                order.setProduct(productOptional.get());
                return orderRepository.save(order);
            } else {
                throw new RuntimeException("Product not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
