package com.example.backendfiveflowers.service.impl;

import com.example.backendfiveflowers.entity.Categories;
import com.example.backendfiveflowers.repository.CategoriesRepository;
import com.example.backendfiveflowers.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }

    @Override
    public Page<Categories> findAll(Pageable pageable) {
        return categoriesRepository.findAll(pageable);
    }

    @Override
    public Optional<Categories> findById(Long id) {
        return categoriesRepository.findById(id);
    }

    @Override
    public Categories save(Categories categories) {
        return categoriesRepository.save(categories);
    }

    @Override
    public void deleteById(Long id) {
        categoriesRepository.deleteById(id);
    }
}
