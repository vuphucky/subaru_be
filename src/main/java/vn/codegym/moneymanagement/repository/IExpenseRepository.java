package vn.codegym.moneymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.codegym.moneymanagement.model.Expense;
@Repository
public interface IExpenseRepository extends JpaRepository<Expense, Long> {}
