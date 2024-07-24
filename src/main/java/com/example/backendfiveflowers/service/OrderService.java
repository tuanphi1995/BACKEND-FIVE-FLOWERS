package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.*;
import com.example.backendfiveflowers.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public Order addOrder(Order order) {
        // Validate and set user
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(order.getUser().getId());
        if (!userInfoOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }
        order.setUser(userInfoOptional.get());

        // Validate and set address
        Optional<Address> addressOptional = addressRepository.findById(order.getAddress().getAddressId());
        if (!addressOptional.isPresent()) {
            throw new RuntimeException("Address not found");
        }
        order.setAddress(addressOptional.get());

        // Handle payment
        Payment payment = order.getPayment();
        if (payment.getPaymentId() != 0) {
            Optional<Payment> paymentOptional = paymentRepository.findById(payment.getPaymentId());
            if (paymentOptional.isPresent()) {
                order.setPayment(paymentOptional.get());
            } else {
                throw new RuntimeException("Payment not found");
            }
        } else {
            payment = paymentRepository.save(payment);
            order.setPayment(payment);
        }

        // Calculate total price and set order details
        double totalOrderPrice = 0.0;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            Optional<Product> productOptional = productRepository.findById(orderDetail.getProduct().getProductId());
            if (!productOptional.isPresent()) {
                throw new RuntimeException("Product not found");
            }
            Product product = productOptional.get();
            orderDetail.setProduct(product);
            orderDetail.setOrder(order);
            orderDetail.setStatus("Pending");

            double detailPrice = product.getPrice() * orderDetail.getQuantity();
            orderDetail.setPrice(detailPrice);
            totalOrderPrice += detailPrice;
        }

        // Add shipping cost to total order price
        double shippingCost = 5.0; // Định nghĩa phí vận chuyển cố định
        order.setShippingCost(shippingCost);
        order.setPrice(totalOrderPrice + shippingCost);

        // Set order status based on input (default to "Pending" if not provided)
        if (order.getStatus() == null || order.getStatus().isEmpty()) {
            order.setStatus("Pending");
        }

        return orderRepository.save(order);
    }


    @Transactional
    public Order updateOrder(Integer id, Order order) {
        Optional<Order> existingOrderOptional = orderRepository.findById(id);
        if (!existingOrderOptional.isPresent()) {
            throw new RuntimeException("Order not found");
        }

        Order existingOrder = existingOrderOptional.get();
        existingOrder.setUser(order.getUser());
        existingOrder.setAddress(order.getAddress());
        existingOrder.setStatus(order.getStatus());

        double totalOrderPrice = 0.0;

        // Xóa các chi tiết đơn hàng cũ
        existingOrder.getOrderDetails().clear();

        // Thêm các chi tiết đơn hàng mới
        for (OrderDetail orderDetail : order.getOrderDetails()) {
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

            existingOrder.getOrderDetails().add(orderDetail); // Thêm chi tiết đơn hàng mới vào đơn hàng
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

    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    public Order updateOrderStatus(Integer id, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            for (OrderDetail detail : order.getOrderDetails()) {
                detail.setStatus(status);
                orderDetailRepository.save(detail);
            }
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }


}
