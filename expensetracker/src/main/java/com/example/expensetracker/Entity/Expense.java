package com.example.expensetracker.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Please provide a description")
    @Size(min = 3, max = 50, message = "Description must be between 3 and 50 characters")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    @DecimalMax(value = "10000001", message = "Amount must be less or equal to 1 crore")
    @Column(nullable = false)
    private double amount;

    @NotBlank(message = "Please provide a date")
    @Column(nullable = false)
    private LocalDateTime date;

    //private String note;

    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)  // Change "id" to "user_id"
    private User user;

}
