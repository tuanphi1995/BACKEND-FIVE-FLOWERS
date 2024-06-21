package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Category;
import com.example.backendfiveflowers.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        if (categoryRepository.existsById(category.getCategoryId())) {
            return categoryRepository.save(category);
        } else {
            throw new RuntimeException("Category with ID " + category.getCategoryId() + " not found");
        }
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    public Optional<Category> getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    public Page<Category> getAllCategories(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return categoryRepository.findAll(pageable);
    }
}
