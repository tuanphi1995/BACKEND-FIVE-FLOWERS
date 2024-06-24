package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.entity.ProductImage;
import com.example.backendfiveflowers.repository.ProductImageRepository;
import com.example.backendfiveflowers.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductImageService {

    private final Path root = Paths.get("uploads");
    private static final Logger logger = LoggerFactory.getLogger(ProductImageService.class);

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

    // Other methods...

    public List<ProductImage> updateImages(int productId, MultipartFile[] files) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found");
        }

        Product product = productOptional.get();
        List<ProductImage> existingImages = productImageRepository.findByProduct(product);
        int existingImageCount = existingImages.size();

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path destinationFile = this.root.resolve(filename).normalize().toAbsolutePath();
            try {
                Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }

            if (i < existingImageCount) {
                ProductImage existingImage = existingImages.get(i);
                existingImage.setImageUrl(filename);
                productImageRepository.save(existingImage);
            } else {
                ProductImage newImage = new ProductImage();
                newImage.setImageUrl(filename);
                newImage.setProduct(product);
                productImageRepository.save(newImage);
            }
        }

        if (files.length < existingImageCount) {
            for (int i = files.length; i < existingImageCount; i++) {
                ProductImage extraImage = existingImages.get(i);
                Path filePath = this.root.resolve(extraImage.getImageUrl()).normalize().toAbsolutePath();
                try {
                    Files.deleteIfExists(filePath);
                } catch (IOException e) {
                    logger.error("Could not delete file: " + filePath, e);
                }
                productImageRepository.delete(extraImage);
            }
        }

        return productImageRepository.findByProduct(product);
    }

    public void deleteProductImage(Integer id) {
        Optional<ProductImage> productImageOptional = productImageRepository.findById(id);
        if (productImageOptional.isPresent()) {
            ProductImage productImage = productImageOptional.get();
            // Delete the image file from the file system
            Path filePath = Paths.get(productImage.getImageUrl());
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                logger.error("Could not delete file: " + filePath, e);
            }
            // Delete the record from the database
            productImageRepository.deleteById(id);
        }
    }

    public Optional<ProductImage> getProductImageById(Integer id) {
        logger.info("Fetching product image with ID: " + id);
        Optional<ProductImage> productImage = productImageRepository.findById(id);
        if (productImage.isPresent()) {
            logger.info("Product image found: " + productImage.get());
        } else {
            logger.warn("Product image not found for ID: " + id);
        }
        return productImage;
    }

    public List<ProductImage> getAllProductImages() {
        return productImageRepository.findAll();
    }

    public List<ProductImage> saveImages(MultipartFile[] files, int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found");
        }

        Product product = productOptional.get();
        List<ProductImage> savedProductImages = new ArrayList<>();

        for (MultipartFile file : files) {
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path destinationFile = this.root.resolve(filename).normalize().toAbsolutePath();
            try {
                Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }

            ProductImage productImage = new ProductImage();
            productImage.setImageUrl(filename);
            productImage.setProduct(product);

            savedProductImages.add(productImageRepository.save(productImage));
        }

        return savedProductImages;
    }

    public List<ProductImage> getProductImagesByProductId(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found");
        }
        Product product = productOptional.get();
        return productImageRepository.findByProduct(product);
    }
}
