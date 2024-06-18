package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductsService {
    List<Products> findAll();

    Page<Products> findAll(Pageable pageable); // Thêm phương thức này
    Optional<Products> findById(Long id);
    Products save(Products products);
    void deleteById(Long id);
}
