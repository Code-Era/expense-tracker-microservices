package com.expense_category_service.controller;


import com.expense_category_service.constant.ExpenseConstant;
import com.expense_category_service.dto.Expense.ExpenseAmount;
import com.expense_category_service.dto.Expense.ExpenseRequestDto;
import com.expense_category_service.dto.Expense.ExpenseResponseDto;
import com.expense_category_service.payload.ApiResponse;
import com.expense_category_service.payload.ResponseService;
import com.expense_category_service.service.expense.ExpenseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/expense/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    @Autowired
    private final ExpenseService expenseService;

    @Autowired
    private Environment env;


    //Authenticated
    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseResponseDto>> createExpense
                                    (@Valid @RequestBody ExpenseRequestDto expenseRequestDto) {
        ExpenseResponseDto expenseResponseDto = expenseService.createExpense(expenseRequestDto);

        return ResponseService.buildSuccessResponse(expenseResponseDto,
                HttpStatus.CREATED.value(),
                ExpenseConstant.EXPENSE_CREATED);
    }

//Authenticated
    @GetMapping()
    public ResponseEntity<ApiResponse<List<ExpenseResponseDto>>> getExpenseAllList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0")  int size,
            @RequestParam(defaultValue = "id")  String sortBy,
            @RequestParam(defaultValue = "asc")  String direction
    ) {
        return ResponseService.buildSuccessResponse(expenseService.getAllExpenses(page, size, sortBy, direction),
                HttpStatus.OK.value(),"Expense Fetched Successfully");

    }

    //Authenticated (only owner)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponseDto>> getExpenseListByd(@Valid @PathVariable Long id) {

        return ResponseService.buildSuccessResponse(expenseService.getExpenseByID(id),
                HttpStatus.OK.value(),"Expense Fetched Successfully");
    }

    //Authenticated (only owner)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponseDto>> updateExpense(@Valid @PathVariable Long id, @Valid @RequestBody ExpenseRequestDto expenseRequestDto) {

        ExpenseResponseDto expenseResponseDto =  expenseService.updateExpense(id, expenseRequestDto);

        return ResponseService.buildSuccessResponse(expenseResponseDto,
                HttpStatus.OK.value(),"Expense updated Successfully");

    }
    //Authenticated (only owner)
    @DeleteMapping("/{id}")
    public  ResponseEntity<ApiResponse<ExpenseResponseDto>> deleteExpense(@Valid @PathVariable Long id) {
        Long expenseId =  expenseService.deleteExpense(id);
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        expenseResponseDto.setId(expenseId);
        return ResponseService.buildSuccessResponse(expenseResponseDto,
                HttpStatus.OK.value(),"Expense deleted Successfully");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/total")
    public ResponseEntity<ApiResponse<ExpenseAmount>> getTotalExpenseForUser(@RequestParam String userEmail) {
        double total =  expenseService.getTotalExpenseForUser(userEmail);
        ExpenseAmount expenseAmount = new ExpenseAmount();
        expenseAmount.setAmount(total);
        return ResponseService.buildSuccessResponse(expenseAmount,
                HttpStatus.OK.value(),  "Expense Fetched Successfully");
    }




    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/total/all")
    public ResponseEntity<ApiResponse<ExpenseAmount>> getTotalExpense() {
        double total = expenseService.getTotalExpenseForAllUsers();

        ExpenseAmount expenseAmount = new ExpenseAmount();
        expenseAmount.setAmount(total);
        return ResponseService.buildSuccessResponse(expenseAmount,
                HttpStatus.OK.value(),  "Expense Fetched Successfully");
    }


    @GetMapping("/java-version")
    public ResponseEntity<ApiResponse<String>> getJavaInfo() {
        String version = env.getProperty("JAVA_HOME") ;
        return ResponseService.buildSuccessResponse(version,
                HttpStatus.OK.value(),  "Java Version Fetched Successfully");
    }


    @GetMapping(value = "/top-expense", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExpenseResponseDto> getTop3Expense(@Valid @RequestParam String userEmail) {

        return expenseService.getTopThreeExpense(userEmail);
    }


}
