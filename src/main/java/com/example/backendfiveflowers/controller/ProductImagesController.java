package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.ProductImages;
import com.example.backendfiveflowers.exception.ResourceNotFoundException;
import com.example.backendfiveflowers.service.ProductImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/productimages")
public class ProductImagesController {

    @Autowired
    private ProductImagesService productImagesService;

    @GetMapping
    public Page<ProductImages> getAllProductImages(Pageable pageable) {
        return productImagesService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<ProductImages> getProductImageById(@PathVariable Long id) {
        return productImagesService.findById(id);
    }

    @PostMapping
    public ProductImages createProductImage(@RequestBody ProductImages productImages) {
        return productImagesService.save(productImages);
    }

    @PutMapping("/{id}")
    public ProductImages updateProductImage(@PathVariable Long id, @RequestBody ProductImages productImageDetails) {
        ProductImages productImages = productImagesService.findById(id).orElseThrow(() -> new ResourceNotFoundException("ProductImage not found"));
        productImages.setImageUrl(productImageDetails.getImageUrl());
        productImages.setProduct(productImageDetails.getProduct());
        return productImagesService.save(productImages);
    }

    @DeleteMapping("/{id}")
    public void deleteProductImage(@PathVariable Long id) {
        productImagesService.deleteById(id);
    }
}
