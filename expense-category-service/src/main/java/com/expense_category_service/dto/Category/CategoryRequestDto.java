package com.expense_category_service.dto.Category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDto {


    @NotBlank(message = "Title is required")
    private String title;


    @NotBlank (message = "Description is required")
    private String description;


}
