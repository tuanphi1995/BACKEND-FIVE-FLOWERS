package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Brand;
import com.example.backendfiveflowers.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/brands")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping("/add")
    public Brand addBrand(@RequestBody Brand brand) {
        return brandService.addBrand(brand);
    }

    @PutMapping("/update/{id}")
    public Brand updateBrand(@PathVariable Integer id, @RequestBody Brand brandDetails) {
        brandDetails.setBrandId(id); // Đảm bảo brand ID được đặt từ URL
        return brandService.updateBrand(brandDetails);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBrand(@PathVariable Integer id) {
        brandService.deleteBrand(id);
    }

    @GetMapping("/get/{id}")
    public Brand getBrandById(@PathVariable Integer id) {
        return brandService.getBrandById(id).orElse(null);
    }

    @GetMapping("/all")
    public Page<Brand> getAllBrands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "brandId") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return brandService.getAllBrands(pageable);
    }
}