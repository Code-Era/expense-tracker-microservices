package com.financeservice.dto.Summary;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyStatDto {
    private String month;
    private double income;
    private double expense;
}
