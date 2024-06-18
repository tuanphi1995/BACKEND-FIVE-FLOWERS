package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Blogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BlogsService {
    List<Blogs> findAll();

    Page<Blogs> findAll(Pageable pageable); // Thêm phương thức này
    Optional<Blogs> findById(Long id);
    Blogs save(Blogs blogs);
    void deleteById(Long id);
}
