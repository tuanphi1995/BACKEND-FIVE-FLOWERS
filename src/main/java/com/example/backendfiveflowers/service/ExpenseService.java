package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Expense;
import com.example.backendfiveflowers.entity.Hour;
import com.example.backendfiveflowers.repository.ExpenseRepository;
import com.example.backendfiveflowers.repository.HourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private HourRepository hourRepository;

    // Thêm mới hoặc cập nhật chi phí và đảm bảo liên kết với giờ (Hour) trong lịch trình
    public Expense saveOrUpdateExpense(Long hourId, Expense expense) {
        // Tìm kiếm giờ (Hour) theo ID
        Hour hour = hourRepository.findById(hourId)
                .orElseThrow(() -> new RuntimeException("Hour not found with id: " + hourId));

        // Gán hour cho expense (chi phí phải thuộc một giờ cụ thể)
        expense.setHour(hour);

        // Lưu hoặc cập nhật expense
        return expenseRepository.save(expense);
    }

    // Lấy chi phí theo ID
    public Expense getExpenseById(Long expenseId) {
        return expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + expenseId));
    }

    // Xóa chi phí theo ID
    public void deleteExpenseById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + expenseId));
        expenseRepository.delete(expense);
    }
}
