package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Payments;
import com.example.backendfiveflowers.exception.ResourceNotFoundException;
import com.example.backendfiveflowers.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    @GetMapping
    public Page<Payments> getAllPayments(Pageable pageable) {
        return paymentsService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Payments> getPaymentById(@PathVariable Long id) {
        return paymentsService.findById(id);
    }

    @PostMapping
    public Payments createPayment(@RequestBody Payments payments) {
        return paymentsService.save(payments);
    }

    @PutMapping("/{id}")
    public Payments updatePayment(@PathVariable Long id, @RequestBody Payments paymentDetails) {
        Payments payments = paymentsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        payments.setOrder(paymentDetails.getOrder());
        payments.setUser(paymentDetails.getUser());
        payments.setPaymentDate(paymentDetails.getPaymentDate());
        payments.setAmount(paymentDetails.getAmount());
        payments.setPaymentMethod(paymentDetails.getPaymentMethod());
        return paymentsService.save(payments);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentsService.deleteById(id);
    }
}
