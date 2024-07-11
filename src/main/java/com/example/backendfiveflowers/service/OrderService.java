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
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(order.getUser().getId());
        if (!userInfoOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }
        order.setUser(userInfoOptional.get());

        Optional<Address> addressOptional = addressRepository.findById(order.getAddress().getAddressId());
        if (!addressOptional.isPresent()) {
            throw new RuntimeException("Address not found");
        }
        order.setAddress(addressOptional.get());

        // Lấy thông tin của Payment từ cơ sở dữ liệu
        Optional<Payment> paymentOptional = paymentRepository.findById(order.getPayment().getPaymentId());
        if (!paymentOptional.isPresent()) {
            throw new RuntimeException("Payment not found");
        }
        order.setPayment(paymentOptional.get());

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

        order.setPrice(totalOrderPrice);
        order.setStatus("Pending");

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
        existingOrder.setStatus(order.getStatus());

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

        Payment payment = order.getPayment();
        if (payment != null) {
            payment = paymentRepository.save(payment);
            existingOrder.setPayment(payment);
        }

        Address address = order.getAddress();
        if (address != null) {
            address = addressRepository.save(address);
            existingOrder.setAddress(address);
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

    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }
}
