package com.example.expensetracker.Entity;

import com.example.expensetracker.Validation.TodayOrYesterday;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(
        name = "Report",
        description = "Details of a report with the user and expense ID"
)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reports")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Username is required")
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "Description cannot be empty")
    @Size(min = 3, max = 100, message = "Description must be between 3 and 100 characters")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    @DecimalMax(value = "10000001", message = "Amount must be less than or equal to 1 crore")
    @Column(nullable = false)
    private double amount;

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date must be in the past or present")
    @TodayOrYesterday
    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false, updatable = false)
    private LocalDateTime generatedAt;

    @NotNull(message = "User cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"expenses"})
    @JsonIgnore
    private User user;

    @NotNull(message = "Expense cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id", nullable = false)
    @JsonIgnoreProperties({"user"})
    @JsonIgnore
    private Expense expense;

    @PrePersist
    public void setGeneratedAt() {
        this.generatedAt = LocalDateTime.now();
    }
}

