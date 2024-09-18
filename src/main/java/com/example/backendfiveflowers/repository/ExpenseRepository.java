package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}