package com.example.quanlytaichinh_be.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table (name= "expensecategory")
public class ExpenseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;
}