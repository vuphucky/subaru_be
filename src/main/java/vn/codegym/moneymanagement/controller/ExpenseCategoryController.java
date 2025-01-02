package vn.codegym.moneymanagement.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.codegym.moneymanagement.model.ExpenseCategory;
import vn.codegym.moneymanagement.service.expensecategory.IExpenseCategoryService;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/expense-categories")
public class ExpenseCategoryController {

    @Autowired
    private IExpenseCategoryService expenseCategoryService;
    @GetMapping
    public ResponseEntity<Iterable<ExpenseCategory>> getAllExpenseCategories() {
        Iterable<ExpenseCategory> categories = expenseCategoryService.findAll();
        if (categories.iterator().hasNext()) {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseCategory> getExpenseCategoryById(@PathVariable Long id) {
        Optional<ExpenseCategory> category = expenseCategoryService.findById(id);
        return category.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<ExpenseCategory> createExpenseCategory(@Valid @RequestBody ExpenseCategory expenseCategory) {
        expenseCategoryService.save(expenseCategory);
        return new ResponseEntity<>(expenseCategory, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseCategory> updateExpenseCategory(@PathVariable Long id, @Valid @RequestBody ExpenseCategory updatedCategory) {
        Optional<ExpenseCategory> existingCategory = expenseCategoryService.findById(id);
        if (existingCategory.isPresent()) {
            updatedCategory.setId(id);
            expenseCategoryService.save(updatedCategory);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteExpenseCategory(@PathVariable Long id) {
        Optional<ExpenseCategory> category = expenseCategoryService.findById(id);
        if (category.isPresent()) {
            expenseCategoryService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

