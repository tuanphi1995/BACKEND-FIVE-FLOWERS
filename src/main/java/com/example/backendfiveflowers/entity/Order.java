package com.example.backendfiveflowers.entity;


import jakarta.persistence.*;


import java.time.LocalDateTime;

import java.util.Set;

@Entity

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long userId;
    private Double totalAmount;
    private LocalDateTime orderDate;
    private String status;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order")
    private Set<OrderCoupon> orderCoupons;

    @OneToMany(mappedBy = "order")
    private Set<Payment> payments;

    @OneToMany(mappedBy = "order")
    private Set<OrderShipping> orderShippings;

    @OneToOne(mappedBy = "order")
    private OrderTracking orderTracking;

    // Getters and Setters
}
