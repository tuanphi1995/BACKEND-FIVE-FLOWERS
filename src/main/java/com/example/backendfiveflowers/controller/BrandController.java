package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Brand;
import com.example.backendfiveflowers.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/brands")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping("/add")
    public Brand addBrand(@RequestBody Brand brand) {
        return brandService.addBrand(brand);
    }

    @PutMapping("/update")
    public Brand updateBrand(@RequestBody Brand brand) {
        return brandService.updateBrand(brand);
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
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }
}
