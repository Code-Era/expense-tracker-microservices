package com.expense_category_service.dto.Category;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cache.annotation.CacheEvict;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CategoryResponseDto {


    @NotBlank(message = "Title is required")
    private String title;

    private Long id;


    @NotBlank (message = "Description is required")
    private String description;


    private String createdBy ;


}
