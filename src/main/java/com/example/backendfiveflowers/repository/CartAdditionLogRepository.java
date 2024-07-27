package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.CartAdditionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CartAdditionLogRepository extends JpaRepository<CartAdditionLog, Long> {
    CartAdditionLog findByDate(LocalDate date);
}
