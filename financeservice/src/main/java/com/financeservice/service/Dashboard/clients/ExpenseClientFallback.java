package com.financeservice.service.Dashboard.clients;

import com.financeservice.dto.ExpenseAmount;
import com.financeservice.dto.ExpenseResponseDto;
import com.financeservice.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ExpenseClientFallback implements ExpenseClient {
    @Override
    public ResponseEntity<ApiResponse<ExpenseAmount>> getTotalExpense() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<ExpenseAmount>> getTotalExpenseForUser(String userEmail) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<String>> getJavaInfo() {
        return null;
    }

    @Override
    public List<ExpenseResponseDto> getTop3Expense(String userEmail) {
        return List.of();
    }
}
