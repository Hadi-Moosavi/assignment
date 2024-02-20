package com.pay.tracker.account.service;

import com.pay.tracker.account.api.AccountDTO;
import com.pay.tracker.account.api.AccountBalanceDTO;
import com.pay.tracker.account.persistance.Account;
import com.pay.tracker.commons.model.User;

import java.util.List;

public interface AccountService {
    AccountDTO saveAccount(AccountDTO dto, User user);

    AccountBalanceDTO getAccountBalance(Long id, User user);

    List<AccountDTO> getUserAccounts(User user);

    void deactivateAccount(Long id, User user);

    Account getAndCheckAccount(Long id, User user);
}
