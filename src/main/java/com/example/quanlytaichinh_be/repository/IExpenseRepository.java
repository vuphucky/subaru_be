package com.example.quanlytaichinh_be.repository;

import com.example.quanlytaichinh_be.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExpenseRepository extends JpaRepository<Expense, Long> {}
