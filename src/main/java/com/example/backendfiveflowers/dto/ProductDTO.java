package com.example.backendfiveflowers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int productId;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String color;
    private String brand;
    private String category;
}
