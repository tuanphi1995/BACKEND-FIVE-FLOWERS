package com.example.backendfiveflowers.service.impl;

import com.example.backendfiveflowers.entity.Brands;
import com.example.backendfiveflowers.repository.BrandsRepository;
import com.example.backendfiveflowers.service.BrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandsServiceImpl implements BrandsService {

    @Autowired
    private BrandsRepository brandsRepository;

    @Override
    public List<Brands> findAll() {
        return brandsRepository.findAll();
    }

    @Override
    public Page<Brands> findAll(Pageable pageable) {
        return brandsRepository.findAll(pageable);
    }

    @Override
    public Optional<Brands> findById(Long id) {
        return brandsRepository.findById(id);
    }

    @Override
    public Brands save(Brands brands) {
        return brandsRepository.save(brands);
    }

    @Override
    public void deleteById(Long id) {
        brandsRepository.deleteById(id);
    }
}
