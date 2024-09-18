package com.example.backendfiveflowers.service;



import com.example.backendfiveflowers.entity.Expense;
import com.example.backendfiveflowers.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Optional<Expense> updateExpense(Long id, Expense expense) {
        return expenseRepository.findById(id).map(existingExpense -> {
            existingExpense.setAmount(expense.getAmount());
            existingExpense.setCategory(expense.getCategory());
            existingExpense.setDate(expense.getDate());
            existingExpense.setNote(expense.getNote());
            return expenseRepository.save(existingExpense);
        });
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}
