package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.entity.ProductImage;
import com.example.backendfiveflowers.repository.ProductImageRepository;
import com.example.backendfiveflowers.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProductImageService {

    private final Path root = Paths.get("uploads");

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductImageService() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public ProductImage addProductImage(ProductImage productImage) {
        Optional<Product> product = productRepository.findById(productImage.getProduct().getProductId());

        if (product.isPresent()) {
            productImage.setProduct(product.get());
            return productImageRepository.save(productImage);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public ProductImage saveImage(MultipartFile file, int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found");
        }

        String filename = file.getOriginalFilename();
        try {
            Files.copy(file.getInputStream(), this.root.resolve(filename));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

        ProductImage productImage = new ProductImage();
        productImage.setImageUrl(this.root.resolve(filename).toString());
        productImage.setProduct(productOptional.get());

        return productImageRepository.save(productImage);
    }

    public ProductImage updateProductImage(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    public void deleteProductImage(Integer id) {
        productImageRepository.deleteById(id);
    }

    public Optional<ProductImage> getProductImageById(Integer id) {
        return productImageRepository.findById(id);
    }

    public List<ProductImage> getAllProductImages() {
        return productImageRepository.findAll();
    }
}
