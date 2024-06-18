package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Reviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReviewsService {
    List<Reviews> findAll();

    Page<Reviews> findAll(Pageable pageable); // Thêm phương thức này
    Optional<Reviews> findById(Long id);
    Reviews save(Reviews reviews);
    void deleteById(Long id);
}
