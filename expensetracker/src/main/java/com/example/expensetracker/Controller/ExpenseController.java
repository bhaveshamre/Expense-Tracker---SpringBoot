package com.example.expensetracker.Controller;

import com.example.expensetracker.Entity.Expense;
import com.example.expensetracker.Service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expense")
public class ExpenseController {


    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PreAuthorize("hasAuthority('Role_USER')")
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getUserExpenses();
    }

    @PreAuthorize("hasAuthority('Role_USER')")
    @GetMapping("/{id}")
    public Optional<Expense> getExpenseById(@Valid @PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    @PreAuthorize("hasAuthority('Role_USER')")
    @PostMapping
    public Expense createExpense(@Valid @RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }

    @PreAuthorize("hasAuthority('Role_USER')")
    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id,@Valid @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    @PreAuthorize("hasAuthority('Role_USER')")
    @DeleteMapping("/{id}")
    public void deleteExpense(@Valid @PathVariable Long id) {
        expenseService.deleteExpense(id); //.orElseThrow(() -> new RuntimeException("Expense not found"));
    }
}

