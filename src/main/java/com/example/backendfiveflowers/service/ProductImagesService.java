package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.ProductImages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductImagesService {
    List<ProductImages> findAll();

    Page<ProductImages> findAll(Pageable pageable); // Thêm phương thức này
    Optional<ProductImages> findById(Long id);
    ProductImages save(ProductImages productImages);
    void deleteById(Long id);
}
