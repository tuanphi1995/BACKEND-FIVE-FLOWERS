package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Payment;
import com.example.backendfiveflowers.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment addPayment(Payment payment) {
        payment.setPaymentDate(LocalDateTime.now());
        System.out.println("Adding payment with method: " + payment.getPaymentMethod());
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Integer id, Payment payment) {
        Optional<Payment> existingPaymentOptional = paymentRepository.findById(id);
        if (existingPaymentOptional.isPresent()) {
            Payment existingPayment = existingPaymentOptional.get();
            existingPayment.setPaymentMethod(payment.getPaymentMethod());
            System.out.println("Updating payment with method: " + payment.getPaymentMethod());
            Payment savedPayment = paymentRepository.save(existingPayment);
            System.out.println("Saved payment method: " + savedPayment.getPaymentMethod());
            return savedPayment;
        } else {
            throw new RuntimeException("Payment not found");
        }
    }

    public void deletePayment(Integer id) {
        paymentRepository.deleteById(id);
    }

    public Optional<Payment> getPaymentById(Integer id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        payments.forEach(payment -> System.out.println("Fetched payment method: " + payment.getPaymentMethod()));
        return payments;
    }
}
