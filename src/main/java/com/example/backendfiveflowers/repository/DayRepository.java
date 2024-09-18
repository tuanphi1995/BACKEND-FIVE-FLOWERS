package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, Long> {
}
