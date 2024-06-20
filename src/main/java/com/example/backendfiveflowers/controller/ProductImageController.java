package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.ProductImage;
import com.example.backendfiveflowers.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/product_images")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @PostMapping("/add")
    public ResponseEntity<ProductImage> addProductImage(@RequestBody ProductImage productImage) {
        ProductImage savedProductImage = productImageService.addProductImage(productImage);
        return ResponseEntity.ok(savedProductImage);
    }

    @PutMapping("/update")
    public ProductImage updateProductImage(@RequestBody ProductImage productImage) {
        return productImageService.updateProductImage(productImage);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductImage(@PathVariable Integer id) {
        productImageService.deleteProductImage(id);
    }

    @GetMapping("/get/{id}")
    public ProductImage getProductImageById(@PathVariable Integer id) {
        return productImageService.getProductImageById(id).orElse(null);
    }

    @GetMapping("/all")
    public List<ProductImage> getAllProductImages() {
        return productImageService.getAllProductImages();
    }

    @PostMapping("/upload/{productId}")
    public ResponseEntity<ProductImage> uploadProductImage(@RequestParam("file") MultipartFile file, @PathVariable int productId) {
        ProductImage savedProductImage = productImageService.saveImage(file, productId);
        return ResponseEntity.ok(savedProductImage);
    }
}
