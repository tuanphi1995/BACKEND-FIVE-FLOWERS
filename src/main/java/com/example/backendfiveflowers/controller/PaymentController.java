package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Payment;
import com.example.backendfiveflowers.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Payment addPayment(@RequestBody Payment payment) {
        return paymentService.addPayment(payment);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Payment updatePayment(@RequestBody Payment payment) {
        return paymentService.updatePayment(payment);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePayment(@PathVariable Integer id) {
        paymentService.deletePayment(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public Payment getPaymentById(@PathVariable Integer id) {
        return paymentService.getPaymentById(id).orElse(null);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
