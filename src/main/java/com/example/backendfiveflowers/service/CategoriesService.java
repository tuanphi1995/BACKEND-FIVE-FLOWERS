package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoriesService {
    List<Categories> findAll();

    Page<Categories> findAll(Pageable pageable); // Thêm phương thức này
    Optional<Categories> findById(Long id);
    Categories save(Categories categories);
    void deleteById(Long id);
}
