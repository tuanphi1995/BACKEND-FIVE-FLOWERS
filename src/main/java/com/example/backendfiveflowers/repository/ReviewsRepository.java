package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Reviews,Long> {
}
