package vn.codegym.moneymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codegym.moneymanagement.model.ExpenseCategory;

public interface IExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {}
