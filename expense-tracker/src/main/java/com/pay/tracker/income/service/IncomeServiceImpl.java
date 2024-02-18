package com.pay.tracker.income.service;

import com.pay.tracker.account.service.AccountService;
import com.pay.tracker.category.persistance.Category;
import com.pay.tracker.category.service.CategoryService;
import com.pay.tracker.commons.model.BusinessException;
import com.pay.tracker.commons.model.User;
import com.pay.tracker.income.api.IncomeDTO;
import com.pay.tracker.income.api.IncomeResponseDTO;
import com.pay.tracker.income.persistance.Income;
import com.pay.tracker.income.persistance.IncomeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final ModelMapper modelMapper;
    private final AccountService accountService;
    private final CategoryService categoryService;

    public IncomeServiceImpl(IncomeRepository incomeRepository, ModelMapper modelMapper, AccountService accountService, CategoryService categoryService) {
        this.incomeRepository = incomeRepository;
        this.modelMapper = modelMapper;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public IncomeResponseDTO saveIncome(IncomeDTO dto, User user) {
        Income income;
        if (dto.getId() != null) {
            income = getAndCheckIncome(dto.getId(), user);
        } else {
            income = new Income();
            income.setUserId(user.getId());
        }
        income.setDescription(dto.getDescription());
        income.setValue(dto.getValue());
        income.setDate(dto.getDate() == null ? LocalDateTime.now() : dto.getDate());
        income.setCategory(categoryService.getAndCheckCategory(dto.getCategoryId(), user));
        income.setAccount(accountService.getAndCheckAccount(dto.getAccountId(), user));
        Income savedModel = incomeRepository.save(income);
        return modelMapper.map(savedModel, IncomeResponseDTO.class);
    }

    @Override
    public IncomeResponseDTO getIncome(Long id, User user) {
        return modelMapper.map(getAndCheckIncome(id, user), IncomeResponseDTO.class);
    }

    @Override
    public List<IncomeResponseDTO> getUserIncomes(Long categoryId, LocalDateTime dateFrom, LocalDateTime dateTo, User user) {
        List<Income> res;
        if (categoryId == null) {
            res = incomeRepository.findByUserIdAndDateBetween(user.getId(), dateFrom, dateTo);
        } else {
            res = incomeRepository.findByUserIdAndCategoryAndDateBetween(user.getId(), new Category(categoryId), dateFrom, dateTo);
        }
        return res.stream()
                .map(a -> modelMapper.map(a, IncomeResponseDTO.class))
                .toList();
    }

    private Income getAndCheckIncome(Long id, User user) {
        Income income;
        income = incomeRepository.findById(id).orElseThrow(() -> new BusinessException("Entity not found."));
        if (!user.getId().equals(income.getUserId())) {
            throw new BusinessException("Account not belong to user");
        }
        return income;
    }
}
