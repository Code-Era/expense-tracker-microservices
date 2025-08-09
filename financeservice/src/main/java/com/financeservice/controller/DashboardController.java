package com.financeservice.controller;



import com.financeservice.aop.LogAspect;
import com.financeservice.dto.ExpenseResponseDto;
import com.financeservice.dto.Summary.AdminDashboardDto;
import com.financeservice.dto.Summary.DashboardSummaryDto;
import com.financeservice.dto.Summary.MonthlyStatDto;
import com.financeservice.payload.ApiResponse;
import com.financeservice.payload.ResponseService;
import com.financeservice.service.Dashboard.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("v1/financial/dashboard/")
@AllArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryDto>> getDashboardSummary() {
        return ResponseService.buildSuccessResponse( dashboardService.getDashboardSummary(),
                HttpStatus.OK.value(), "Dashboard Fetched Successfully");
    }



    @GetMapping("/monthly")
    public ResponseEntity<ApiResponse<List<MonthlyStatDto>>> getMonthlySummary() {
        return ResponseService.buildSuccessResponse( dashboardService.getMonthlySummary(),
                HttpStatus.OK.value(), "Dashboard Monthly Fetched Successfully");

    }


    @GetMapping("/top-expense")
    public ResponseEntity<ApiResponse<List<ExpenseResponseDto>>> getTopExpense() {
        return ResponseService.buildSuccessResponse( dashboardService.getTopThreeExpense(),
                HttpStatus.OK.value(), "Top Expense Fetched Successfully");
    }




    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/overview")
    @LogAspect
    public ResponseEntity<ApiResponse<AdminDashboardDto>> getAdminDashboard() {
        return ResponseService.buildSuccessResponse( dashboardService.getAdminDashboard(),
                HttpStatus.OK.value(), "Admin Dashboard Fetched Successfully");

    }


}
