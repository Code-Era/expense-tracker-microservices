package com.expense_category_service.dto.Expense;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExpenseRequestDto {


    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Amount is required")
    private Double amount;


    private Long categoryId;

    @NotBlank (message = "Description is required")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

}
