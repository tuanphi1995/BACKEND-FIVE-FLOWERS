package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.entity.ProductImage;
import com.example.backendfiveflowers.repository.ProductRepository;
import com.example.backendfiveflowers.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product_images")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductRepository productRepository;


    @PutMapping("/update/{id}")
    public ResponseEntity<ProductImage> updateProductImage(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        ProductImage updatedProductImage = productImageService.updateImage(id, file);
        return ResponseEntity.ok(updatedProductImage);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Integer id) {
        productImageService.deleteProductImage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductImage> getProductImageById(@PathVariable Integer id) {
        Optional<ProductImage> productImage = productImageService.getProductImageById(id);
        return productImage.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductImage>> getAllProductImages() {
        List<ProductImage> productImages = productImageService.getAllProductImages();
        return ResponseEntity.ok(productImages);
    }

    @PostMapping("/upload/{productId}")
    public ResponseEntity<Map<String, Object>> uploadProductImages(@RequestParam("files") MultipartFile[] files, @PathVariable int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Product product = productOptional.get();
        List<ProductImage> savedProductImages = productImageService.saveImages(files, productId);

        Map<String, Object> response = new HashMap<>();
        response.put("product", product);
        response.put("productImages", savedProductImages);

        return ResponseEntity.ok(response);
    }
}
