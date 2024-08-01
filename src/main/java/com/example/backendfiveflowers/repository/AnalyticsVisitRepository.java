package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.AnalyticsVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AnalyticsVisitRepository extends JpaRepository<AnalyticsVisit, Long> {
    int countByVisitTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
