package com.pay.tracker.account.service;

import com.pay.tracker.account.api.AccountDTO;
import com.pay.tracker.account.api.AccountBalanceDTO;
import com.pay.tracker.commons.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    void getAccountInfo() {
        var dto = new AccountDTO();
        dto.setInitialBalance(100L);
        dto.setName("test");
        var user = new User(1L, "test");
        dto = accountService.saveAccount(dto, user);
        AccountBalanceDTO accountInfo = accountService.getAccountBalance(dto.getId(), user);
        assertEquals(100L, accountInfo.getCurrentBalance());
    }
}