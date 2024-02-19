package com.pay.tracker.summary.service;

import com.pay.tracker.account.api.AccountDTO;
import com.pay.tracker.account.service.AccountService;
import com.pay.tracker.category.api.CategoryDTO;
import com.pay.tracker.category.api.CategoryResponseDTO;
import com.pay.tracker.category.persistance.TransactionTypeEnum;
import com.pay.tracker.category.service.CategoryService;
import com.pay.tracker.commons.model.User;
import com.pay.tracker.summary.api.SummaryDTO;
import com.pay.tracker.transaction.api.TransactionDTO;
import com.pay.tracker.transaction.api.TransactionResponseDTO;
import com.pay.tracker.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SummaryServiceTest {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SummaryService summaryService;

    private User user = new User(1L, "test");

    @Test
    void getSummaryByCategory() {

        var categoryDTO = new CategoryDTO();
            categoryDTO.setTransactionTypeCode(TransactionTypeEnum.INCOME.getCode());
            categoryDTO.setName("INC1");
            var categoryResponseDTO = categoryService.saveCategory(categoryDTO, user);

            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setName("ACC1");
            accountDTO.setInitialBalance(0L);
            var accountResponse = accountService.saveAccount(accountDTO, user);

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
        var summary = summaryService.getSummaryByCategory(from, to, TransactionTypeEnum.INCOME.getCode(),
                accountResponse.getId(), user);
        assertEquals(1, summary.getTotalCount());
        assertEquals(1000L, summary.getTotalBalance());
        assertEquals(TransactionTypeEnum.INCOME, summary.getList().get(0).getItem().getType());

    }

    @Test
    void getSummaryByAccount() {

        var categoryDTO = new CategoryDTO();
        categoryDTO.setTransactionTypeCode(TransactionTypeEnum.EXPENSE.getCode());
        categoryDTO.setName("INC2");
        var categoryResponseDTO = categoryService.saveCategory(categoryDTO, user);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setName("ACC2");
        accountDTO.setInitialBalance(0L);
        var accountResponse = accountService.saveAccount(accountDTO, user);

        TransactionDTO dto = new TransactionDTO();
        dto.setCategoryId(categoryResponseDTO.getId());
        dto.setDescription("test");
        dto.setAmount(1000L);
        dto.setAccountId(accountResponse.getId());
        dto.setTransactionTypeCode(TransactionTypeEnum.EXPENSE.getCode());
        TransactionResponseDTO transactionResponseDTO = transactionService.saveTransaction(dto, user);
        assertNotNull(transactionResponseDTO);
        assertEquals(1000L, transactionResponseDTO.getAmount());


        var from = LocalDateTime.now().minusHours(1);
        var to = LocalDateTime.now();
        var summary=summaryService.getSummaryByAccount(from, to, null,
                null, user);
        assertEquals(2, summary.getTotalCount());
        assertEquals(0L, summary.getTotalBalance());
    }

    @Test
    void getSummaryByType() {

        var categoryDTO = new CategoryDTO();
        categoryDTO.setTransactionTypeCode(TransactionTypeEnum.INCOME.getCode());
        categoryDTO.setName("INC3");
        var categoryResponseDTO = categoryService.saveCategory(categoryDTO, user);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setName("ACC3");
        accountDTO.setInitialBalance(0L);
        var accountResponse = accountService.saveAccount(accountDTO, user);

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
        var summary=summaryService.getSummaryByType(from, to,
                null, null, user);
        assertEquals(1, summary.getTotalCount());
        assertEquals(1000L, summary.getTotalBalance());
        assertEquals(TransactionTypeEnum.INCOME.name(), summary.getList().get(0).getItem());
    }
}