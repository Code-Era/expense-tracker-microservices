package com.expense_category_service.mapper;



import com.expense_category_service.dto.Category.CategoryResponseDto;
import com.expense_category_service.dto.Expense.ExpenseRequestDto;
import com.expense_category_service.dto.Expense.ExpenseResponseDto;
import com.expense_category_service.entity.Expense;
import com.expense_category_service.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class ExpenseMapper {

    @Autowired
    private SessionUtil sessionUtil;

    public static Expense mapToExpenseEntity(ExpenseRequestDto expenseRequestDto) {
        Expense expense= new Expense();
        expense.setTitle(expenseRequestDto.getTitle());
        expense.setAmount(expenseRequestDto.getAmount());
        expense.setDate(LocalDate.now());
        expense.setDescription(expenseRequestDto.getDescription());
        return expense;
    }

    public static ExpenseResponseDto mapToExpenseResponseDto(Expense expense) {
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        expenseResponseDto.setId(expense.getId());
        expenseResponseDto.setTitle(expense.getTitle());
        expenseResponseDto.setAmount(expense.getAmount());
        CategoryResponseDto categoryDto = new CategoryResponseDto();

        categoryDto.setId(expense.getCategory().getId());
        categoryDto.setTitle(expense.getCategory().getTitle());
        categoryDto.setDescription(expense.getCategory().getDescription());

        expenseResponseDto.setCategoryResponseDto(categoryDto);
        expenseResponseDto.setDescription(expense.getDescription());
      //  expenseResponseDto.setDate(expense.getDate().toString());
        expenseResponseDto.setCreatedByEmail(expense.getUserEmail());
        return expenseResponseDto;
    }
}
