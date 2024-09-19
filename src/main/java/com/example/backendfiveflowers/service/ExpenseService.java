package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Expense;
import com.example.backendfiveflowers.entity.Hour;
import com.example.backendfiveflowers.repository.ExpenseRepository;
import com.example.backendfiveflowers.repository.HourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private HourRepository hourRepository;

    // Thêm hoặc cập nhật chi phí (Expense) cho một giờ
    public Expense saveOrUpdateExpense(Long hourId, Expense expense) {
        Hour hour = hourRepository.findById(hourId)
                .orElseThrow(() -> new RuntimeException("Hour not found with id: " + hourId));
        expense.setHour(hour);  // Gán giờ cho chi phí
        return expenseRepository.save(expense);
    }

    // Sửa chi phí (Expense) độc lập
    public Expense updateExpense(Long expenseId, Expense updatedExpense) {
        Expense existingExpense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + expenseId));

        if (updatedExpense.getAmount() != null) {
            existingExpense.setAmount(updatedExpense.getAmount());
        }

        if (updatedExpense.getCategory() != null) {
            existingExpense.setCategory(updatedExpense.getCategory());
        }

        if (updatedExpense.getNote() != null) {
            existingExpense.setNote(updatedExpense.getNote());
        }

        return expenseRepository.save(existingExpense);
    }

    // Lấy chi phí (Expense) theo ID
    public Expense getExpenseById(Long expenseId) {
        return expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + expenseId));
    }

    // Xóa chi phí (Expense) theo ID
    public void deleteExpenseById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + expenseId));
        expenseRepository.delete(expense);
    }
}
