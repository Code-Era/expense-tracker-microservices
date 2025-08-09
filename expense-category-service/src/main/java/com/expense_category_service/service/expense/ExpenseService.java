package com.expense_category_service.service.expense;



import com.expense_category_service.dto.Expense.ExpenseRequestDto;
import com.expense_category_service.dto.Expense.ExpenseResponseDto;

import java.util.List;

public interface ExpenseService {

    /**
     * Create a new expense.
     * @param expenseRequestDto Request payload
     * @return Created expense DTO
     */
    ExpenseResponseDto createExpense(ExpenseRequestDto expenseRequestDto);

    /**
     * Get all expenses of the currently logged-in user.
     * @return List of ExpenseResponseDto
     */
    List<ExpenseResponseDto> getAllExpenses();

    /**
     * Get a specific expense by ID, only if the user owns it.
     * @param id Expense ID
     * @return ExpenseResponseDto
     */
    ExpenseResponseDto getExpenseByID(Long id);

    /**
     * Update an expense with new data.
     * @param id Expense ID
     * @param expenseRequestDto Updated data
     * @return Updated ExpenseResponseDto
     */
    ExpenseResponseDto updateExpense(long id, ExpenseRequestDto expenseRequestDto);


    /**
     * Delete an expense by ID.
     * @param id Expense ID
     * @return ID of deleted expense (for confirmation)
     */
    long deleteExpense(long id);


    List<ExpenseResponseDto> getAllExpenses(int page, int size, String sortBy, String direction);



    Double getTotalExpenseForUser(String userEmail);

    Double getTotalExpenseForAllUsers();

    List<ExpenseResponseDto> getTopThreeExpense(String email);
}
