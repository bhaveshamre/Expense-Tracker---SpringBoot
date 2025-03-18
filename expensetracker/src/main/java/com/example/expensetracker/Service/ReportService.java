package com.example.expensetracker.Service;

import com.example.expensetracker.Entity.Expense;
import com.example.expensetracker.Entity.Report;
import com.example.expensetracker.Repository.ExpenseRepository;
import com.example.expensetracker.Repository.ReportRepository;
import com.example.expensetracker.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;


    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // **1. User-wise Expense Summary Report**
    public Map<String, Double> getUserExpenseSummary() {
        List<Report> reports = reportRepository.findAll();

        return reports.stream()
                .collect(Collectors.groupingBy(
                        Report::getUsername,
                        Collectors.summingDouble(Report::getAmount)
                ));
    }

    // **2. Date-wise Expense Report**
    public Map<LocalDate, List<Report>> getExpensesByDate() {
        List<Report> reports = reportRepository.findAll();

        return reports.stream()
                .collect(Collectors.groupingBy(
                        report -> report.getDate().toLocalDate()
                ));
    }

    @Transactional // Ensures Hibernate fetches all data before session closes
    public List<Report> getAllReports() {
        List<Report> reports = reportRepository.findAll();

        // Force Hibernate to load lazy fields
        reports.forEach(report -> {
            report.getUser().getName();
            report.getUser().getUsername(); // Load user data
            report.getExpense().getDescription(); // Load expense data
        });

        return reports;
    }


}
