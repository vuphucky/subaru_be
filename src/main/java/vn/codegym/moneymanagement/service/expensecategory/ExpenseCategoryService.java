package vn.codegym.moneymanagement.service.expensecategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.codegym.moneymanagement.model.ExpenseCategory;
import vn.codegym.moneymanagement.repository.IExpenseCategoryRepository;

import java.util.List;
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
    public void save(ExpenseCategory expenseCategory) {
        expenseCategoryRepository.save(expenseCategory);
    }

    @Override
    public void delete(Long id) {
        expenseCategoryRepository.deleteById(id);
    }
}
