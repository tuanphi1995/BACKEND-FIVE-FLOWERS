package com.example.backendfiveflowers.model;

public class CartItemRequest {
    private Long productId;
    private int quantity;
    private String imageUrl;
    private String name; // Thêm tên sản phẩm
    private String brand; // Thêm thương hiệu
    private String category; // Thêm danh mục
    private double price; // Thêm giá sản phẩm

    // Constructors
    public CartItemRequest() {}

    public CartItemRequest(Long productId, int quantity, String imageUrl, String name, String brand, String category, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
