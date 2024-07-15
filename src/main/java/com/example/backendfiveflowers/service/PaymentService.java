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

    private boolean sandboxEnabled = false;

    public Payment addPayment(Payment payment) {
        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Integer id, Payment payment) {
        Optional<Payment> existingPaymentOptional = paymentRepository.findById(id);
        if (existingPaymentOptional.isPresent()) {
            Payment existingPayment = existingPaymentOptional.get();
            existingPayment.setPaymentMethod(payment.getPaymentMethod());
            return paymentRepository.save(existingPayment);
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
        return paymentRepository.findAll();
    }

    public boolean isSandboxEnabled() {
        return sandboxEnabled;
    }

    public void setSandboxEnabled(boolean enabled) {
        this.sandboxEnabled = enabled;
    }
}
