package com.financeservice.dto.Summary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashboardDto {

    private long totalUsers;
    private double totalIncome;
    private double totalExpense;
    private double netBalance;

}
