package com.expense_category_service.controller;




import com.expense_category_service.constant.ResponseConstants;
import com.expense_category_service.dto.Category.CategoryRequestDto;
import com.expense_category_service.dto.Category.CategoryResponseDto;
import com.expense_category_service.payload.ApiResponse;
import com.expense_category_service.payload.ResponseService;
import com.expense_category_service.service.Category.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/expense/categories")
@RequiredArgsConstructor
public class CategoryController {

   private final CategoryService categoryService;

    //Authenticated
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDto>> createCategory
                                    (@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryRequestDto);

        return ResponseService.buildSuccessResponse(categoryResponseDto,
                HttpStatus.CREATED.value(),
                ResponseConstants.Resource_CREATED);
    }

//Authenticated

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<CategoryResponseDto>>> getCategoryAllList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0")  int size,
            @RequestParam(defaultValue = "id")  String sortBy,
            @RequestParam(defaultValue = "asc")  String direction
    ) {
        /*return ResponseService.buildSuccessResponse(categoryService.getAllCategory(page, size, sortBy, direction),
                HttpStatus.OK.value(),"Category Fetched Successfully");*/
        return ResponseService.buildSuccessResponse(categoryService.getAllCategory(),
                HttpStatus.OK.value(),"Category Fetched Successfully");

    }

    //Authenticated (only owner)
    @Cacheable
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> getCategoryListByd(@Valid @PathVariable Long id) {

        return ResponseService.buildSuccessResponse(categoryService.getCategoryByID(id),
                HttpStatus.OK.value(),"Category Fetched Successfully");
    }

    //Authenticated (only owner)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> updateCategory(@Valid @PathVariable Long id,
                                                                         @Valid @RequestBody
                                                                         CategoryRequestDto categoryRequestDto) {

        CategoryResponseDto categoryRequestDto1 =  categoryService.updateCategory(id, categoryRequestDto);

        return ResponseService.buildSuccessResponse(categoryRequestDto1,
                HttpStatus.OK.value(),"Category updated Successfully");

    }
    //Authenticated (only owner)
    @DeleteMapping("/{id}")
    public  ResponseEntity<ApiResponse<CategoryResponseDto>> deleteCategory(@Valid @PathVariable Long id) {
        Long categoryId =  categoryService.deleteCategory(id);
        CategoryResponseDto expenseResponseDto = new CategoryResponseDto();
        expenseResponseDto.setId(categoryId);
        return ResponseService.buildSuccessResponse(expenseResponseDto,
                HttpStatus.OK.value(),"Expense deleted Successfully");
    }

}
