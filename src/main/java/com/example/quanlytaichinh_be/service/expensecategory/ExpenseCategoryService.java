package com.example.quanlytaichinh_be.service.expensecategory;
import com.example.quanlytaichinh_be.model.ExpenseCategory;
import com.example.quanlytaichinh_be.model.Wallet;
import com.example.quanlytaichinh_be.repository.IExpenseCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseCategoryService implements IExpenseCategoryService {

    @Autowired
    private IExpenseCategoryRepository expenseCategoryRepository;

    @Override
    public Iterable<ExpenseCategory> findAll() {
        return expenseCategoryRepository.findAll();
    }

    @Override
    public Optional<ExpenseCategory> findById(Long id) {
        return expenseCategoryRepository.findById(id);
    }

    @Override
    public Wallet save(ExpenseCategory expenseCategory) {
        expenseCategoryRepository.save(expenseCategory);
        return null;
    }

    @Override
    public void delete(Long id) {
        expenseCategoryRepository.deleteById(id);
    }
}
