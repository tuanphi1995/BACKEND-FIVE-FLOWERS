package com.example.backendfiveflowers.exception;

public class OrderDetailsNotFoundException extends RuntimeException {
    public OrderDetailsNotFoundException(String message) {
        super(message);
    }
}
