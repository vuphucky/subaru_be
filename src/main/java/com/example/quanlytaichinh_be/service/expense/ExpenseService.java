package com.example.quanlytaichinh_be.service.expense;

import com.example.quanlytaichinh_be.model.Expense;
import com.example.quanlytaichinh_be.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.quanlytaichinh_be.repository.IExpenseRepository;

import java.util.Optional;

@Service
public class ExpenseService implements IExpenseService {
    @Autowired
    private  IExpenseRepository expenseRepository;


    @Override
    public Iterable<Expense> findAll() {
       return expenseRepository.findAll();
    }

    @Override
    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public Wallet save(Expense expense) {
        expenseRepository.save(expense);
        return null;
    }

    @Override
    public void delete(Long id) {
        expenseRepository.deleteById(id);
    }
}