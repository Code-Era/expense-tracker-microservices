package com.expense_category_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ExpenseCategoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseCategoryServiceApplication.class, args);
	}

}
