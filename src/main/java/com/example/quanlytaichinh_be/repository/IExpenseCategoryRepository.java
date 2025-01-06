package com.example.quanlytaichinh_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.quanlytaichinh_be.model.ExpenseCategory;

public interface IExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {}
