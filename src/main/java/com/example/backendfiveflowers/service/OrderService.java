package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Order;
import com.example.backendfiveflowers.entity.OrderDetail;
import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.repository.OrderRepository;
import com.example.backendfiveflowers.repository.UserInfoRepository;
import com.example.backendfiveflowers.repository.ProductRepository;
import com.example.backendfiveflowers.repository.OrderDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public Order addOrder(Order order) {
        logger.info("Attempting to add order for user ID: {}", order.getUser().getId());

        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(order.getUser().getId());
        if (!userInfoOptional.isPresent()) {
            logger.error("User not found with ID: {}", order.getUser().getId());
            throw new RuntimeException("User not found");
        }

        Optional<Product> productOptional = productRepository.findById(order.getProduct().getProductId());
        if (!productOptional.isPresent()) {
            logger.error("Product not found with ID: {}", order.getProduct().getProductId());
            throw new RuntimeException("Product not found");
        }

        order.setUser(userInfoOptional.get());
        order.setProduct(productOptional.get());

        if (order.getOrderDetail() == null) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderDate(LocalDateTime.now());
            orderDetail.setShippingStatus("Pending");
            orderDetail.setStatus("New");
            orderDetail.setTotalAmount(order.getPrice() * order.getQuantity());
            orderDetail.setUser(order.getUser());

            order.setOrderDetail(orderDetail);
        } else {
            OrderDetail existingOrderDetail = order.getOrderDetail();
            existingOrderDetail.setTotalAmount(existingOrderDetail.getTotalAmount() + order.getPrice() * order.getQuantity());
        }

        return orderRepository.save(order);
    }

    public Order updateOrder(Integer id, Order order) {
        Optional<Order> existingOrderOptional = orderRepository.findById(id);
        if (!existingOrderOptional.isPresent()) {
            throw new RuntimeException("Order not found");
        }

        Order existingOrder = existingOrderOptional.get();

        existingOrder.setQuantity(order.getQuantity());
        existingOrder.setPrice(order.getPrice());

        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(order.getUser().getId());
        if (!userInfoOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }

        Optional<Product> productOptional = productRepository.findById(order.getProduct().getProductId());
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found");
        }

        existingOrder.setUser(userInfoOptional.get());
        existingOrder.setProduct(productOptional.get());

        if (existingOrder.getOrderDetail() == null) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderDate(LocalDateTime.now());
            orderDetail.setShippingStatus("Pending");
            orderDetail.setStatus("New");
            orderDetail.setTotalAmount(order.getPrice() * order.getQuantity());
            orderDetail.setUser(order.getUser());
            existingOrder.setOrderDetail(orderDetail);
        } else {
            OrderDetail existingOrderDetail = existingOrder.getOrderDetail();
            existingOrderDetail.setTotalAmount(existingOrderDetail.getTotalAmount() + order.getPrice() * order.getQuantity());
        }

        return orderRepository.save(existingOrder);
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
}
