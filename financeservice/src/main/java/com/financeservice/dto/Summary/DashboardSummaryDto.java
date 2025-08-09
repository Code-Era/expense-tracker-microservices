package com.financeservice.dto.Summary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardSummaryDto {

    private double totalIncome;
    private double totalExpense;
    private double netBalance;
}
