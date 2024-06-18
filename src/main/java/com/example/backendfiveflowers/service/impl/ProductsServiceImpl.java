package com.example.backendfiveflowers.service.impl;

import com.example.backendfiveflowers.entity.Products;
import com.example.backendfiveflowers.repository.ProductsRepository;
import com.example.backendfiveflowers.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<Products> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public Page<Products> findAll(Pageable pageable) {
        return productsRepository.findAll(pageable);
    }

    @Override
    public Optional<Products> findById(Long id) {
        return productsRepository.findById(id);
    }

    @Override
    public Products save(Products products) {
        return productsRepository.save(products);
    }

    @Override
    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }
}
