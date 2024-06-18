package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Reviews,Long> {
}
