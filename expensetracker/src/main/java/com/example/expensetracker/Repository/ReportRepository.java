package com.example.expensetracker.Repository;

import com.example.expensetracker.Entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    // Find reports by User ID
    List<Report> findByUserId(Long userId);

    // Find reports by Date
    List<Report> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // Find report by Expense ID
    Report findByExpenseId(Long expenseId);

    // Delete report when expense is deleted
    void deleteByExpenseId(Long expenseId);
}
