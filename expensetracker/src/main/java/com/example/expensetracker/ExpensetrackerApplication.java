package com.example.expensetracker;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @io.swagger.v3.oas.annotations.info.Info(
				title = "Expense Tracker API",
				version = "1.0",
				description = "API for Expense Tracker",
				contact = @io.swagger.v3.oas.annotations.info.Contact(
						name = "Bhavesh Amre",
						email = "bhavesh.amre@freestoneinfotech.com"
		)
)
)
@SpringBootApplication
public class ExpensetrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpensetrackerApplication.class, args);
	}

}
