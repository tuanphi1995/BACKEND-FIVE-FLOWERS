package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Brand;
import com.example.backendfiveflowers.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand addBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand updateBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public void deleteBrand(Integer id) {
        brandRepository.deleteById(id);
    }

    public Optional<Brand> getBrandById(Integer id) {
        return brandRepository.findById(id);
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
}
