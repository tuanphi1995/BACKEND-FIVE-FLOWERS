package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {
}
