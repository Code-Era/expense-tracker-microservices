package com.financeservice.service.Income;




import com.financeservice.dto.Income.IncomeRequestDto;
import com.financeservice.dto.Income.IncomeResponseDto;

import java.util.List;


public interface IncomeService {

    IncomeResponseDto addIncome(IncomeRequestDto incomeRequestDto);

    List<IncomeResponseDto> getAllIncomesList();

    IncomeResponseDto getIncomeById(Long id);

    IncomeResponseDto updateIncome(Long id , IncomeRequestDto incomeRequestDto);

    long deleteIncome(Long id);



}
