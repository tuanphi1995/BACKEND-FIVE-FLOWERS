package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.dto.ProductDTO;
import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.mapper.ProductMapper;
import com.example.backendfiveflowers.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @PostMapping("/add")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(productMapper.toDTO(savedProduct));
    }
    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/get/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id).orElse(null);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
