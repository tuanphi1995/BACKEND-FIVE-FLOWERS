package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Brands;
import com.example.backendfiveflowers.exception.ResourceNotFoundException;
import com.example.backendfiveflowers.service.BrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandsController {

    @Autowired
    private BrandsService brandsService;

    @GetMapping
    public Page<Brands> getAllBrands(Pageable pageable) {
        return brandsService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Brands> getBrandById(@PathVariable Long id) {
        return brandsService.findById(id);
    }

    @PostMapping
    public Brands createBrand(@RequestBody Brands brands) {
        return brandsService.save(brands);
    }

    @PutMapping("/{id}")
    public Brands updateBrand(@PathVariable Long id, @RequestBody Brands brandDetails) {
        Brands brands = brandsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
        brands.setName(brandDetails.getName());
        brands.setDescription(brandDetails.getDescription());
        return brandsService.save(brands);
    }

    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Long id) {
        brandsService.deleteById(id);
    }
}
