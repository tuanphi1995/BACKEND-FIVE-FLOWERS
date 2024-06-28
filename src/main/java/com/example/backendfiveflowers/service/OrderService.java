package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Order;
import com.example.backendfiveflowers.entity.OrderDetail;
import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.repository.OrderRepository;
import com.example.backendfiveflowers.repository.UserInfoRepository;
import com.example.backendfiveflowers.repository.ProductRepository;
import com.example.backendfiveflowers.repository.OrderDetailRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    @Transactional
    public Order addOrder(Order order) {
        if (order.getUser() == null || order.getUser().getId() == null) {
            throw new RuntimeException("Thông tin người dùng bị thiếu trong đơn hàng.");
        }

        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(order.getUser().getId());
        if (!userInfoOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy người dùng với ID: " + order.getUser().getId());
        }

        order.setUser(userInfoOptional.get());

        double totalOrderPrice = 0.0;

        for (OrderDetail orderDetail : order.getOrderDetails()) {
            Optional<Product> productOptional = productRepository.findById(orderDetail.getProduct().getProductId());
            if (!productOptional.isPresent()) {
                throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + orderDetail.getProduct().getProductId());
            }
            Product product = productOptional.get();
            orderDetail.setProduct(product);
            orderDetail.setOrder(order);
            orderDetail.setStatus("Pending");

            double detailPrice = product.getPrice() * orderDetail.getQuantity();
            orderDetail.setPrice(detailPrice);
            totalOrderPrice += detailPrice;
        }

        order.setOrderDetails(order.getOrderDetails());
        order.setPrice(totalOrderPrice);

        return orderRepository.save(order);
    }

    public Order updateOrder(Integer id, Order order) {
        Optional<Order> existingOrderOptional = orderRepository.findById(id);
        if (!existingOrderOptional.isPresent()) {
            throw new RuntimeException("Order not found");
        }

        Order existingOrder = existingOrderOptional.get();
        existingOrder.setOrderDetails(order.getOrderDetails());
        existingOrder.setUser(order.getUser());

        double totalOrderPrice = 0.0;

        for (OrderDetail orderDetail : existingOrder.getOrderDetails()) {
            Optional<Product> productOptional = productRepository.findById(orderDetail.getProduct().getProductId());
            if (!productOptional.isPresent()) {
                throw new RuntimeException("Product not found");
            }
            Product product = productOptional.get();
            orderDetail.setProduct(product);
            orderDetail.setOrder(existingOrder);

            double detailPrice = product.getPrice() * orderDetail.getQuantity();
            orderDetail.setPrice(detailPrice);
            totalOrderPrice += detailPrice;
        }

        existingOrder.setPrice(totalOrderPrice);

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
