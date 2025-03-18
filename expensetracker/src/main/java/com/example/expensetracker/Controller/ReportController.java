package com.example.expensetracker.Controller;

import com.example.expensetracker.Entity.Report;
import com.example.expensetracker.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


//@PreAuthorize("hasAuthority('Role_ADMIN')")
@RestController
@RequestMapping("/admin/reports")
public class ReportController {

    @Autowired
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    //@PreAuthorize("hasAuthority('Role_ADMIN')")
    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    // **1. Get User-wise Expense Summary**
    @PreAuthorize("hasAuthority('Role_ADMIN')")
    @GetMapping("/summary")
    public Map<String, Double> getUserExpenseSummary() {
        return reportService.getUserExpenseSummary();
    }

    // **2. Get Date-wise Expense Report**
    @PreAuthorize("hasAuthority('Role_ADMIN')")
    @GetMapping("/by-date")
    public Map<LocalDate, List<Report>> getExpensesByDate() {
        return reportService.getExpensesByDate();
    }
}

