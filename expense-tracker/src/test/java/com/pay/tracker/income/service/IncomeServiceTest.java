package com.pay.tracker.income.service;

import com.pay.tracker.account.api.AccountDTO;
import com.pay.tracker.account.service.AccountService;
import com.pay.tracker.category.api.CategoryDTO;
import com.pay.tracker.category.api.CategoryResponseDTO;
import com.pay.tracker.category.persistance.TransactionTypeEnum;
import com.pay.tracker.category.service.CategoryService;
import com.pay.tracker.commons.model.User;
import com.pay.tracker.income.api.IncomeDTO;
import com.pay.tracker.income.api.IncomeResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IncomeServiceTest {

    @Autowired
    private IncomeService incomeService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CategoryService categoryService;

    private User user = new User(1L, "test");
    CategoryResponseDTO categoryResponseDTO;
    AccountDTO accountResponse;

    @BeforeEach
    void setUp() {
        var categoryDTO = new CategoryDTO();
        if (categoryResponseDTO == null) {
            categoryDTO.setTransactionTypeCode(TransactionTypeEnum.INCOME.getCode());
            categoryDTO.setName("INC1");
            categoryResponseDTO = categoryService.saveCategory(categoryDTO, user);
        }
        if (accountResponse == null) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setName("ACC1");
            accountDTO.setInitialBalance(0L);
            accountResponse = accountService.saveAccount(accountDTO, user);
        }
    }

    @Test
    void saveIncomeAndRetrieve() {
        IncomeDTO dto = new IncomeDTO();
        dto.setCategoryId(categoryResponseDTO.getId());
        dto.setDescription("test");
        dto.setAmount(1000L);
        dto.setAccountId(accountResponse.getId());
        IncomeResponseDTO incomeResponseDTO = incomeService.saveIncome(dto, user);
        assertNotNull(incomeResponseDTO);
        assertEquals(1000L, incomeResponseDTO.getAmount());


        List<IncomeResponseDTO> userIncomes = incomeService.getUserIncomes(categoryResponseDTO.getId(),
                LocalDateTime.now().minusHours(1), LocalDateTime.now(), user);
        assertEquals(1, userIncomes.size());
    }
}