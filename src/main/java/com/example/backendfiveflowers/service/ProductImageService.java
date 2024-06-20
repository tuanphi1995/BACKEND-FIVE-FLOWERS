package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.ProductImage;
import com.example.backendfiveflowers.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    public ProductImage addProductImage(ProductImage productImage) {
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
