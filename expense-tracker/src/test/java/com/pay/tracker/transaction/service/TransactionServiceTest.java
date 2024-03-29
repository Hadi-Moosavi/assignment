package com.pay.tracker.transaction.service;

import com.pay.tracker.account.api.AccountDTO;
import com.pay.tracker.account.service.AccountService;
import com.pay.tracker.category.api.CategoryDTO;
import com.pay.tracker.category.api.CategoryResponseDTO;
import com.pay.tracker.category.persistance.TransactionTypeEnum;
import com.pay.tracker.category.service.CategoryService;
import com.pay.tracker.commons.model.User;
import com.pay.tracker.transaction.api.TransactionDTO;
import com.pay.tracker.transaction.api.TransactionResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;
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
        TransactionDTO dto = new TransactionDTO();
        dto.setCategoryId(categoryResponseDTO.getId());
        dto.setDescription("test");
        dto.setAmount(1000L);
        dto.setAccountId(accountResponse.getId());
        dto.setTransactionTypeCode(TransactionTypeEnum.INCOME.getCode());
        TransactionResponseDTO transactionResponseDTO = transactionService.saveTransaction(dto, user);
        assertNotNull(transactionResponseDTO);
        assertEquals(1000L, transactionResponseDTO.getAmount());


        var from = LocalDateTime.now().minusHours(1);
        var to = LocalDateTime.now();
        var trans = transactionService.filter(from, to, TransactionTypeEnum.INCOME.getCode(),
                categoryResponseDTO.getId(), accountResponse.getId(), user);
        assertEquals(1, trans.size());
        trans = transactionService.filter(from, to, TransactionTypeEnum.INCOME.getCode(),
                categoryResponseDTO.getId(), null, user);
        assertEquals(1, trans.size());
        trans = transactionService.filter(from, to, TransactionTypeEnum.INCOME.getCode(),
                categoryResponseDTO.getId(), 2L, user);
        assertEquals(0, trans.size());
    }
}