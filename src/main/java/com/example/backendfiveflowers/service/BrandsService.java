package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Brands;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BrandsService {
    List<Brands> findAll();

    Page<Brands> findAll(Pageable pageable); // Thêm phương thức này
    Optional<Brands> findById(Long id);
    Brands save(Brands brands);
    void deleteById(Long id);
}
