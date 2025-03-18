package com.example.expensetracker.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class TodayOrYesterdayValidator implements ConstraintValidator<TodayOrYesterday, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime date, ConstraintValidatorContext context) {
        if (date == null) {
            return false; // Ensure date is not null
        }

        LocalDate inputDate = date.toLocalDate();
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        return inputDate.equals(today) || inputDate.equals(yesterday);
    }
}
