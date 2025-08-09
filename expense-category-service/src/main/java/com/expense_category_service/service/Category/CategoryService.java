package com.expense_category_service.service.Category;





import com.expense_category_service.dto.Category.CategoryRequestDto;
import com.expense_category_service.dto.Category.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    /**
     *Create a new expense
     * @param categoryRequestDto
     * @return
     */
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    /**
     * Get all expenses, only if the user owns them
     */
    List<CategoryResponseDto> getAllCategory();

    /**
     * Get a specific expense by ID, only if the user owns it.
     */
    CategoryResponseDto getCategoryByID(Long id);

    /**
     * Update an expense with new data.
     * @param id Expense ID
     * @param categoryRequestDto Updated data
     */
    CategoryResponseDto updateCategory(long id, CategoryRequestDto categoryRequestDto);


    /**
     * Delete an expense by ID.
     * @param id
     */
    long deleteCategory(long id);

    /**
     * get All category with Pagination and Sorting
     * @param page
     * @param size
     * @param sortBy
     * @param direction
     * @return
     */
    List<CategoryResponseDto> getAllCategory(int page, int size, String sortBy, String direction);
}
