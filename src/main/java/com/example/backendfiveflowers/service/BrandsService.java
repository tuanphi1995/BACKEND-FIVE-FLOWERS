package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Brands;

import java.util.List;
import java.util.Optional;

public interface BrandsService {
    List<Brands> findAll();

    Optional<Brands> findById(Long id);

    Brands save(Brands brands);

    void deleteById(Long id);
}
