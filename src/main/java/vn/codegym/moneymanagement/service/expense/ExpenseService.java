package vn.codegym.moneymanagement.service.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.codegym.moneymanagement.model.Expense;
import vn.codegym.moneymanagement.repository.IExpenseRepository;

import java.util.List;
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
    public void save(Expense expense) {
        expenseRepository.save(expense);
    }

    @Override
    public void delete(Long id) {
        expenseRepository.deleteById(id);
    }
}