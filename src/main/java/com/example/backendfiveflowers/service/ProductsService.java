package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Products;

import java.util.List;
import java.util.Optional;

public interface ProductsService {
    List<Products> findAll();

    Optional<Products> findById(Long id);

    Products save(Products products);

    void deleteById(Long id);
}
