package com.example.expensetracker.Service;

import com.example.expensetracker.Entity.Expense;
import com.example.expensetracker.Entity.Report;
import com.example.expensetracker.Repository.ExpenseRepository;
import com.example.expensetracker.Repository.ReportRepository;
import com.example.expensetracker.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, ReportRepository reportRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public List<Expense> getUserExpenses() {
        String username = getCurrentUsername();
        return expenseRepository.findByUserId(userRepository.findByUsername(username).get().getId());
    }

    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    @Transactional
    public Expense addExpense(Expense expense) {
        String username = getCurrentUsername();
        expense.setUser(userRepository.findByUsername(username).get());
        Expense savedExpense = expenseRepository.save(expense);

        // Generate a report when an expense is added
        Report report = new Report();
        report.setName(expense.getUser().getName());
        report.setUsername(savedExpense.getUser().getUsername());
        report.setDescription(savedExpense.getDescription());
        report.setAmount(savedExpense.getAmount());
        report.setDate(savedExpense.getDate());
        report.setUser(savedExpense.getUser());
        report.setExpense(savedExpense);
        reportRepository.save(report);

        return savedExpense;
    }

    @Transactional
    public Expense updateExpense(Long id, Expense updatedExpense) {
        return expenseRepository.findById(id).map(expense -> {
            expense.setDescription(updatedExpense.getDescription());
            expense.setAmount(updatedExpense.getAmount());
            expense.setDate(updatedExpense.getDate());

            Expense savedExpense = expenseRepository.save(expense);

            // Update the existing report
            Report report = reportRepository.findByExpenseId(savedExpense.getId());
            if (report == null) {
                report = new Report();
            }
            report.setName(expense.getUser().getName());
            report.setUsername(savedExpense.getUser().getUsername());
            report.setDescription(savedExpense.getDescription());
            report.setAmount(savedExpense.getAmount());
            report.setDate(savedExpense.getDate());
            report.setUser(savedExpense.getUser());
            report.setExpense(savedExpense);
            reportRepository.save(report);

            return savedExpense;
        }).orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    @Transactional
    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        // Delete the corresponding report
        reportRepository.deleteByExpenseId(expense.getId());

        expenseRepository.delete(expense);
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Fetches logged-in user's username
    }
}
