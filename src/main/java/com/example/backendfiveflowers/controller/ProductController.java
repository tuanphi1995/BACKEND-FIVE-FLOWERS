package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.entity.ProductImage;
import com.example.backendfiveflowers.service.ProductImageService;
import com.example.backendfiveflowers.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        System.out.println("Received product: " + product);
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) {
        productDetails.setProductId(id); // Đảm bảo ID của product được lấy từ đường dẫn
        Product updatedProduct = productService.updateProduct(productDetails);

        // Cập nhật các URL ảnh sản phẩm nếu có
        if (productDetails.getProductImages() != null && !productDetails.getProductImages().isEmpty()) {
            List<String> imageUrls = productDetails.getProductImages().stream()
                    .map(ProductImage::getImageUrl)
                    .collect(Collectors.toList());
            productImageService.updateExistingImages(productDetails.getProductId(), imageUrls);
        }

        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/update/{id}/images")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> updateProductImages(@PathVariable int id, @RequestParam("files") MultipartFile[] files) {
        List<ProductImage> updatedProductImages = productImageService.updateImages(id, files);

        Map<String, Object> response = new HashMap<>();
        response.put("productId", id);
        response.put("productImages", updatedProductImages);

        return ResponseEntity.ok(response);
    }



    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add/images/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> uploadProductImages(@RequestParam("files") MultipartFile[] files, @PathVariable int productId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ProductImage> uploadedImages = productImageService.saveImages(files, productId);
            response.put("message", "Images uploaded successfully");
            response.put("productId", productId);
            response.put("productImages", uploadedImages);
        } catch (Exception e) {
            response.put("message", "Failed to upload images");
            response.put("error", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping("/add/existing-images/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> addExistingImages(@PathVariable int productId, @RequestBody List<String> imageUrls) {
        productImageService.addExistingImages(productId, imageUrls);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reduceQuantity/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> reduceQuantity(@PathVariable Integer id, @RequestParam int quantity) {
        productService.reduceQuantity(id, quantity);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.searchProducts(query, pageable);
        return ResponseEntity.ok(products);
    }
}