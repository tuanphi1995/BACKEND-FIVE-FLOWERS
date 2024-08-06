package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {
    Page<Product> findByNameContainingAndIsDeletedFalse(String name, Pageable pageable);

    Page<Product> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Product> findByProductIdAndIsDeletedFalse(Integer productId);
}

