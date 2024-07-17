package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.ProductImage;
import com.example.backendfiveflowers.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/product_images")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @PutMapping("/update/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> updateProductImages(@PathVariable int productId, @RequestParam("files") MultipartFile[] files) {
        List<ProductImage> updatedProductImages = productImageService.updateImages(productId, files);

        Map<String, Object> response = new HashMap<>();
        response.put("productId", productId);
        response.put("productImages", updatedProductImages);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Integer id) {
        productImageService.deleteProductImage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductImage> getProductImageById(@PathVariable Integer id) {
        return productImageService.getProductImageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductImage>> getAllProductImages() {
        List<ProductImage> productImages = productImageService.getAllProductImages();
        // Không thêm phần "/api/v1/images/" vào đường dẫn ảnh
        return ResponseEntity.ok(productImages);
    }

    @GetMapping("/byProduct/{productId}")
    public ResponseEntity<List<ProductImage>> getProductImagesByProductId(@PathVariable int productId) {
        List<ProductImage> productImages = productImageService.getProductImagesByProductId(productId);
        return ResponseEntity.ok(productImages);
    }
}
