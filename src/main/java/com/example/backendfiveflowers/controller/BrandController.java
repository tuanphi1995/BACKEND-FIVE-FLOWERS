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
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    public Brand addBrand(@RequestBody Brand brand) {
        return brandService.addBrand(brand);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public Brand updateBrand(@PathVariable Integer id, @RequestBody Brand brandDetails) {
        brandDetails.setBrandId(id); // Ensure brand ID is set from URL
        return brandService.updateBrand(brandDetails);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteBrand(@PathVariable Integer id) {
        brandService.deleteBrand(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
