package com.financeservice.dto.Income;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class IncomeRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotBlank (message = "Category is required")
    private String category;

    private String description;

    @Column(name = "date")
    private LocalDate date ;




}
