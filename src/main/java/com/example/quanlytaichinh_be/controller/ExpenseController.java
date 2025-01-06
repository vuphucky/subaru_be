package com.example.quanlytaichinh_be.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.quanlytaichinh_be.model.Expense;
import com.example.quanlytaichinh_be.service.expense.IExpenseService;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin("*")
public class ExpenseController {

    @Autowired
    private IExpenseService expenseService;

    @GetMapping
    public ResponseEntity<Iterable<Expense>> getAllExpenses() {
        Iterable<Expense> expenses = expenseService.findAll();
        if (expenses.iterator().hasNext()) {
            return new ResponseEntity<>(expenses, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Optional<Expense> expense = expenseService.findById(id);
        return expense.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) {
        expenseService.save(expense);
        return new ResponseEntity<>(expense, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @Valid @RequestBody Expense updatedExpense) {
        Optional<Expense> existingExpense = expenseService.findById(id);
        if (existingExpense.isPresent()) {
            updatedExpense.setId(id);
            expenseService.save(updatedExpense);
            return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteExpense(@PathVariable Long id) {
        Optional<Expense> expense = expenseService.findById(id);
        if (expense.isPresent()) {
            expenseService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
