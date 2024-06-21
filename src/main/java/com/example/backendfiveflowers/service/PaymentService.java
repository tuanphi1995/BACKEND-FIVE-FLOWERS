package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.OrderDetail;
import com.example.backendfiveflowers.entity.Payment;
import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.repository.OrderDetailRepository;
import com.example.backendfiveflowers.repository.PaymentRepository;
import com.example.backendfiveflowers.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public Payment addPayment(Payment payment) {
        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Integer id, Payment payment) {
        Optional<Payment> existingPaymentOptional = paymentRepository.findById(id);
        if (!existingPaymentOptional.isPresent()) {
            throw new RuntimeException("Payment not found");
        }

        Payment existingPayment = existingPaymentOptional.get();
        existingPayment.setAmount(payment.getAmount());
        existingPayment.setPaymentMethod(payment.getPaymentMethod());
        existingPayment.setPaymentDate(LocalDateTime.now()); // Set the current timestamp

        // Fetch and set orderDetail
        if (payment.getOrderDetail() != null && payment.getOrderDetail().getOrderDetailId() != 0) {
            Optional<OrderDetail> orderDetailOptional = orderDetailRepository.findById(payment.getOrderDetail().getOrderDetailId());
            if (orderDetailOptional.isPresent()) {
                existingPayment.setOrderDetail(orderDetailOptional.get());
            }
        }

        // Fetch and set user
        if (payment.getUser() != null && payment.getUser().getId() != 0) {
            Optional<UserInfo> userOptional = userInfoRepository.findById(payment.getUser().getId());
            if (userOptional.isPresent()) {
                existingPayment.setUser(userOptional.get());
            }
        }

        return paymentRepository.save(existingPayment);
    }

    public void deletePayment(Integer id) {
        paymentRepository.deleteById(id);
    }

    public Optional<Payment> getPaymentById(Integer id) {
        return paymentRepository.findById(id);
    }

    public Page<Payment> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }
}
