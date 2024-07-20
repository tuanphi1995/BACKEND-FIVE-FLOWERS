package com.example.backendfiveflowers.model;

public class CartItemRequest {
    private Long productId;
    private int quantity;
    private String imageUrl;

    // Constructors
    public CartItemRequest() {}

    public CartItemRequest(Long productId, int quantity, String imageUrl) {
        this.productId = productId;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
