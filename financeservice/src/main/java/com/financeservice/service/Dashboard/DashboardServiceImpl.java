package com.financeservice.service.Dashboard;


import com.financeservice.dto.ExpenseAmount;
import com.financeservice.dto.ExpenseResponseDto;
import com.financeservice.dto.Summary.AdminDashboardDto;
import com.financeservice.dto.Summary.DashboardSummaryDto;
import com.financeservice.dto.Summary.MonthlyStatDto;
import com.financeservice.entity.Income;
import com.financeservice.payload.ApiResponse;
import com.financeservice.repository.IncomeRepository;

import com.financeservice.service.Dashboard.clients.ExpenseClient;
import com.financeservice.service.Dashboard.clients.UserFeignClient;
import com.financeservice.util.SessionUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DashboardServiceImpl  implements DashboardService {

    private final SessionUtil sessionUtil;

    private final IncomeRepository  incomeRepository;

    @Autowired
    private ExpenseClient expenseClient;

    @Autowired
    private UserFeignClient userFeignClient;



    @Override
    public DashboardSummaryDto getDashboardSummary() {
        String email = sessionUtil.getCurrentUserEmail();
        boolean isAdmin = sessionUtil.isCurrentUserAdmin();

       Double totalIncome;
        Double totalExpense = 0.0;
        if (isAdmin) {
            totalIncome = incomeRepository.getTotalIncomeForAllUserEmail();
            ResponseEntity<ApiResponse<ExpenseAmount>> amountResponseEntity = expenseClient.getTotalExpense();
            //System.out.println("Amount: =" + amountResponseEntity.getBody().getPayload());
            totalExpense =amountResponseEntity.getBody().getPayload().getAmount();
        } else {
            totalIncome = incomeRepository.getTotalIncomeByUserEmail(email);
            totalExpense = expenseClient.getTotalExpenseForUser(email).getBody().getPayload().getAmount();
        }

       DashboardSummaryDto dashboardSummaryDto = new DashboardSummaryDto();
       dashboardSummaryDto.setTotalExpense(totalExpense);
       dashboardSummaryDto.setTotalIncome(totalIncome);
       dashboardSummaryDto.setNetBalance(totalIncome - totalExpense);

        return dashboardSummaryDto;
    }

    @Override
    public List<MonthlyStatDto> getMonthlySummary() {
        String email = sessionUtil.getCurrentUserEmail();
        boolean isAdmin = sessionUtil.isCurrentUserAdmin();

       List<Income> income =  incomeRepository.findAll();
       Double totalExpense =  expenseClient.getTotalExpenseForUser(email).getBody().getPayload().getAmount();

       Map<Integer, Double> incomeMap = income.stream()

                .collect(Collectors.groupingBy(
                        in -> in.getDate().getMonthValue(),
                        Collectors.summingDouble(Income::getAmount)
                ));


       List<MonthlyStatDto> monthlyStatDtoList = new ArrayList<>();

       for(int i = 1; i <= 12; i++) {
           MonthlyStatDto monthlyStatDto = new MonthlyStatDto();
           monthlyStatDto.setMonth(Month.of(i).name());
           monthlyStatDto.setIncome(incomeMap.getOrDefault(i, 0.0));
           monthlyStatDto.setExpense(totalExpense);
           monthlyStatDtoList.add(monthlyStatDto);

       }


        return monthlyStatDtoList;
    }

    @Override
    public List<ExpenseResponseDto> getTopThreeExpense() {
        String email = sessionUtil.getCurrentUserEmail();
      return  expenseClient.getTop3Expense(email);
    }

    @Override
    public AdminDashboardDto getAdminDashboard() {
        //user
        long count = userFeignClient.getAllUsersCount().getBody().getPayload();
        
        //expense
        ResponseEntity<ApiResponse<ExpenseAmount>> expense = expenseClient.getTotalExpense();
        Double totalExpense = expense.getBody().getPayload().getAmount();

        //income
        Double totalIncome = incomeRepository.getTotalIncomeForAllUserEmail();

        AdminDashboardDto adminDashboardDto = new AdminDashboardDto();
        adminDashboardDto.setTotalUsers(count);
        adminDashboardDto.setTotalExpense(totalExpense);
        adminDashboardDto.setTotalIncome(totalIncome);
        adminDashboardDto.setNetBalance(totalIncome - totalExpense);
        return adminDashboardDto;
    }


}
