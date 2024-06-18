package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Products;
import com.example.backendfiveflowers.exception.ResourceNotFoundException;
import com.example.backendfiveflowers.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping
    public Page<Products> getAllProducts(Pageable pageable) {
        return productsService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Products> getProductById(@PathVariable Long id) {
        return productsService.findById(id);
    }

    @PostMapping
    public Products createProduct(@RequestBody Products products) {
        return productsService.save(products);
    }

    @PutMapping("/{id}")
    public Products updateProduct(@PathVariable Long id, @RequestBody Products productDetails) {
        Products products = productsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        products.setName(productDetails.getName());
        products.setDescription(productDetails.getDescription());
        products.setPrice(productDetails.getPrice());
        products.setColor(productDetails.getColor());
        products.setQuantity(productDetails.getQuantity());
        products.setCategory(productDetails.getCategory());
        products.setBrand(productDetails.getBrand());
        return productsService.save(products);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productsService.deleteById(id);
    }
}
