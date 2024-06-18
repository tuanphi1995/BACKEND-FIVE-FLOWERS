package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
