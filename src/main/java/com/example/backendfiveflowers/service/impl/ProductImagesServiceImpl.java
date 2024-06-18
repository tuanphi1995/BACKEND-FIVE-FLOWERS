package com.example.backendfiveflowers.service.impl;

import com.example.backendfiveflowers.entity.ProductImages;
import com.example.backendfiveflowers.repository.ProductImagesRepository;
import com.example.backendfiveflowers.service.ProductImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImagesServiceImpl implements ProductImagesService {

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Override
    public List<ProductImages> findAll() {
        return productImagesRepository.findAll();
    }

    @Override
    public Optional<ProductImages> findById(Long id) {
        return productImagesRepository.findById(id);
    }

    @Override
    public ProductImages save(ProductImages productImages) {
        return productImagesRepository.save(productImages);
    }

    @Override
    public void deleteById(Long id) {
        productImagesRepository.deleteById(id);
    }
}
