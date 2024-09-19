package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByHourId(Long hourId);


}
