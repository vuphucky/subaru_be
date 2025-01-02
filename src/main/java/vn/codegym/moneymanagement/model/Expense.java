package vn.codegym.moneymanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ExpenseCategory category;

    private String wallet;

    private String note;

    private LocalDateTime timestamp = LocalDateTime.now();
}