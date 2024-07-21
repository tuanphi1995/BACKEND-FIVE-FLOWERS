package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.model.CartItemRequest;
import com.example.backendfiveflowers.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/sync")
    public ResponseEntity<?> syncCart(@RequestBody List<CartItemRequest> cartItems, Principal principal) {
        cartService.syncCart(cartItems, principal.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getCart(Principal principal) {
        return ResponseEntity.ok(cartService.getCart(principal.getName()));
    }
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(Principal principal) {
        cartService.clearCart(principal.getName());
        return ResponseEntity.ok().build();
    }
}
