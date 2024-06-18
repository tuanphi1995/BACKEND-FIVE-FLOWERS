package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.ProductImages;

import java.util.List;
import java.util.Optional;

public interface ProductImagesService {
    List<ProductImages> findAll();

    Optional<ProductImages> findById(Long id);

    ProductImages save(ProductImages productImages);

    void deleteById(Long id);
}
