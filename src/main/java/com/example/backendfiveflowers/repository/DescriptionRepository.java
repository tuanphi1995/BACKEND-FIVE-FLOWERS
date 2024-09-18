package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Description;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionRepository extends JpaRepository<Description, Long> {
}

