package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Expense;
import com.example.backendfiveflowers.entity.Hour;
import com.example.backendfiveflowers.repository.ExpenseRepository;
import com.example.backendfiveflowers.repository.HourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private HourRepository hourRepository;

    // Lưu hoặc cập nhật chi phí cho một giờ cụ thể
    public Expense saveOrUpdateExpense(Long hourId, Expense expense) {
        Hour hour = hourRepository.findById(hourId)
                .orElseThrow(() -> new RuntimeException("Hour not found with id: " + hourId));

        // Nếu amount là null, đặt giá trị mặc định
        if (expense.getAmount() == null) {
            expense.setAmount(0.0); // Đặt giá trị mặc định cho amount
        }

        expense.setHour(hour);
        return expenseRepository.save(expense);
    }


    // Lấy danh sách chi phí của một giờ cụ thể
    public List<Expense> getExpensesByHourId(Long hourId) {
        return expenseRepository.findByHourId(hourId);
    }

    // Sửa chi phí
    public Expense updateExpense(Long expenseId, Expense updatedExpense) {
        Expense existingExpense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + expenseId));
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setCategory(updatedExpense.getCategory());
        existingExpense.setNote(updatedExpense.getNote());
        return expenseRepository.save(existingExpense);
    }

    // Lấy chi phí theo ID
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
    }

    // Xóa chi phí theo ID
    public void deleteExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        expenseRepository.delete(expense);
    }
}

