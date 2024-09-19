package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Expense;
import com.example.backendfiveflowers.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // Thêm hoặc cập nhật chi phí (Expense)
    @PostMapping("/save/{hourId}")
    public ResponseEntity<Expense> saveOrUpdateExpense(@PathVariable Long hourId, @RequestBody Expense expense) {
        Expense savedExpense = expenseService.saveOrUpdateExpense(hourId, expense);
        return ResponseEntity.ok(savedExpense);
    }

    // Sửa chi phí (Expense) độc lập
    @PutMapping("/update/{expenseId}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long expenseId, @RequestBody Expense updatedExpense) {
        Expense updated = expenseService.updateExpense(expenseId, updatedExpense);
        return ResponseEntity.ok(updated);
    }

    // Lấy chi phí theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);
        return ResponseEntity.ok(expense);
    }

    // Xóa chi phí theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExpenseById(@PathVariable Long id) {
        expenseService.deleteExpenseById(id);
        return ResponseEntity.ok("Expense deleted successfully");
    }
}
