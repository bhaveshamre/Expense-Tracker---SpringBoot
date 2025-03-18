package com.example.expensetracker.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TodayOrYesterdayValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TodayOrYesterday {
    String message() default "Date must be today or one day back";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
