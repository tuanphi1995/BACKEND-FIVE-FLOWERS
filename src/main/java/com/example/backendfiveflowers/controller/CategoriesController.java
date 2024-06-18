package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Categories;
import com.example.backendfiveflowers.exception.ResourceNotFoundException;
import com.example.backendfiveflowers.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping
    public Page<Categories> getAllCategories(Pageable pageable) {
        return categoriesService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Categories> getCategoryById(@PathVariable Long id) {
        return categoriesService.findById(id);
    }

    @PostMapping
    public Categories createCategory(@RequestBody Categories categories) {
        return categoriesService.save(categories);
    }

    @PutMapping("/{id}")
    public Categories updateCategory(@PathVariable Long id, @RequestBody Categories categoryDetails) {
        Categories categories = categoriesService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categories.setName(categoryDetails.getName());
        categories.setDescription(categoryDetails.getDescription());
        return categoriesService.save(categories);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoriesService.deleteById(id);
    }
}
