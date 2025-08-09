package com.financeservice.dto;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseResponseDto {

    private Long id;
    private String title;
    private Double amount;
    private CategoryResponseDto categoryResponseDto;
    private String description;

    private String createdByEmail;
}
