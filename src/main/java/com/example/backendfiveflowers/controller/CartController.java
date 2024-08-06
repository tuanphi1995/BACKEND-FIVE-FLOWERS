package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.model.CartItemRequest;
import com.example.backendfiveflowers.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeCartItem(Principal principal, @PathVariable Long productId) {
        cartService.removeCartItem(principal.getName(), productId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/stats")
    public ResponseEntity<?> getCartStats(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        int addToCartCount = cartService.getTotalAddToCartByDate(localDate);
        return ResponseEntity.ok(Map.of("addToCart", addToCartCount));
    }

    @GetMapping("/stats/range")
    public ResponseEntity<?> getCartStatsByRange(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        int addToCartCount = cartService.getTotalAddToCartByDateRange(start, end);
        return ResponseEntity.ok(Map.of("addToCart", addToCartCount));
    }
}
