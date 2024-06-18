package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Reviews;

import java.util.List;
import java.util.Optional;

public interface ReviewsService {
    List<Reviews> findAll();

    Optional<Reviews> findById(Long id);

    Reviews save(Reviews reviews);

    void deleteById(Long id);
}
