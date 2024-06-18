package com.example.backendfiveflowers.service.impl;

import com.example.backendfiveflowers.entity.Reviews;
import com.example.backendfiveflowers.repository.ReviewsRepository;
import com.example.backendfiveflowers.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewsServiceImpl implements ReviewsService {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Override
    public List<Reviews> findAll() {
        return reviewsRepository.findAll();
    }

    @Override
    public Page<Reviews> findAll(Pageable pageable) {
        return reviewsRepository.findAll(pageable);
    }

    @Override
    public Optional<Reviews> findById(Long id) {
        return reviewsRepository.findById(id);
    }

    @Override
    public Reviews save(Reviews reviews) {
        return reviewsRepository.save(reviews);
    }

    @Override
    public void deleteById(Long id) {
        reviewsRepository.deleteById(id);
    }
}
