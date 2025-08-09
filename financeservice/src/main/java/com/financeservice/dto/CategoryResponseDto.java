package com.financeservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

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
