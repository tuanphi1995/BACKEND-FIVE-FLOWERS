package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Brands;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brands, Long> {
}
