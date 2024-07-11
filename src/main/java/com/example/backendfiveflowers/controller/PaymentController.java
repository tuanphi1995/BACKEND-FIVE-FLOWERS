package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Payment;
import com.example.backendfiveflowers.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    public Payment addPayment(@RequestBody Payment payment) {
        return paymentService.addPayment(payment);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public Payment updatePayment(@PathVariable Integer id, @RequestBody Payment payment) {
        return paymentService.updatePayment(id, payment);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deletePayment(@PathVariable Integer id) {
        paymentService.deletePayment(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/get/{id}")
    public Payment getPaymentById(@PathVariable Integer id) {
        return paymentService.getPaymentById(id).orElse(null);
    }

    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        payments.forEach(payment -> System.out.println("Payment fetched in controller: " + payment.getPaymentMethod()));
        return payments;
    }
}
