package com.pay.tracker.income.service;

import com.pay.tracker.commons.model.User;
import com.pay.tracker.income.api.IncomeDTO;
import com.pay.tracker.income.api.IncomeResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IncomeService {
    IncomeResponseDTO saveIncome(IncomeDTO dto, User user);

    IncomeResponseDTO getIncome(Long id, User user);

    List<IncomeResponseDTO> getUserIncomes(Long categoryId, LocalDateTime dateFrom, LocalDateTime dateTo, User user);
}
