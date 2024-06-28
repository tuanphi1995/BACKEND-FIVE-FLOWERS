package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Category;
import com.example.backendfiveflowers.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PutMapping("/update/{id}")
    public Category updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        category.setCategoryId(id); // Đảm bảo rằng ID được truyền từ URL được đặt vào đối tượng category
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/get/{id}")
    public Category getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id).orElse(null);
    }

    @GetMapping("/all")
    public Page<Category> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "categoryId") String sortBy) {
        return categoryService.getAllCategories(page, size, sortBy);
    }
}