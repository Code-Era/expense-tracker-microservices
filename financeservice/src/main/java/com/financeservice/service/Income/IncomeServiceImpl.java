package com.financeservice.service.Income;


import com.financeservice.dto.Income.IncomeRequestDto;
import com.financeservice.dto.Income.IncomeResponseDto;
import com.financeservice.entity.Income;
import com.financeservice.exception.ExceptionUtil;
import com.financeservice.mapper.IncomeMapper;
import com.financeservice.repository.IncomeRepository;
import com.financeservice.util.SessionUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
@AllArgsConstructor
public class IncomeServiceImpl  implements IncomeService {

    private final IncomeRepository incomeRepository;

    private final SessionUtil sessionUtil;

    @Override
    public IncomeResponseDto addIncome(IncomeRequestDto incomeRequestDto) {

        String userEmail = sessionUtil.getCurrentUserEmail();

        incomeRepository.findByTitleAndUserEmail(incomeRequestDto.getTitle(), userEmail)
                    . ifPresent(income ->
                            ExceptionUtil.duplicate("Income", incomeRequestDto.getTitle()));


        Income income = IncomeMapper.mapToIncomeEntity(incomeRequestDto);
        income.setUserEmail(userEmail);

        Income incomeSaved = incomeRepository.save(income);
        if(incomeSaved == null)
            throw ExceptionUtil.throwSaveFailed("Income");

        return IncomeMapper.mapToIncomeResponseDto(incomeSaved);
    }

    @Override
    public List<IncomeResponseDto> getAllIncomesList() {
        String  user = sessionUtil.getCurrentUserEmail();

        return incomeRepository.findAllByUserEmail(user)
                .stream()
                .map(IncomeMapper::mapToIncomeResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public IncomeResponseDto getIncomeById(Long id) {

        Income income = incomeRepository.findById(id)
                .orElseThrow(
                        () -> ExceptionUtil.throwResourceNotFound("Income", String.valueOf(id))
                );

        String userEmail = sessionUtil.getCurrentUserEmail();

      if(!income.getUserEmail().equals(userEmail)){
           ExceptionUtil.throwAccessDenied("You are not authorized to view this income");
      }

      return IncomeMapper.mapToIncomeResponseDto(income);

    }

    @Override
    public IncomeResponseDto updateIncome(Long id, IncomeRequestDto incomeRequestDto) {

        Income income = incomeRepository.findById(id)
                .orElseThrow(()-> ExceptionUtil.throwResourceNotFound("Income", String.valueOf(id)));

        if(!income.getUserEmail().equals(sessionUtil.getCurrentUserEmail())){
            throw ExceptionUtil.throwAccessDenied("You are not authorized to view this income");
        }

        income.setTitle(incomeRequestDto.getTitle());
        income.setAmount(incomeRequestDto.getAmount());
        income.setDescription(incomeRequestDto.getDescription());
        income.setCategory(incomeRequestDto.getCategory());

       Income income1= incomeRepository.save(income);

        return IncomeMapper.mapToIncomeResponseDto(income1);

    }

    @Override
    public long deleteIncome(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(()-> ExceptionUtil.throwResourceNotFound("Income", String.valueOf(id)));

        if(!income.getUserEmail().equals(sessionUtil.getCurrentUserEmail())){
            throw ExceptionUtil.throwAccessDenied("You are not authorized to view this income");
        }

        incomeRepository.deleteById(id);
        return id;
    }
}
