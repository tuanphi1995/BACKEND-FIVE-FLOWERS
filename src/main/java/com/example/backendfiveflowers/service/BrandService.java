package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Brand;
import com.example.backendfiveflowers.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand addBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand updateBrand(Brand brandDetails) {
        Optional<Brand> existingBrand = brandRepository.findById(brandDetails.getBrandId());
        if (existingBrand.isPresent()) {
            Brand brand = existingBrand.get();
            brand.setName(brandDetails.getName());
            brand.setDescription(brandDetails.getDescription()); // Ensure description is updated
            return brandRepository.save(brand);
        } else {
            throw new RuntimeException("Brand not found");
        }
    }

    public void deleteBrand(Integer id) {
        brandRepository.deleteById(id);
    }

    public Optional<Brand> getBrandById(Integer id) {
        return brandRepository.findById(id);
    }

    public Page<Brand> getAllBrands(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }
}
