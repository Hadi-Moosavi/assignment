package com.pay.tracker.account.service;

import com.pay.tracker.account.api.AccountDTO;
import com.pay.tracker.commons.model.User;

import java.util.List;

public interface AccountService {
    AccountDTO saveAccount(AccountDTO dto, User user);

    AccountDTO getAccount(Long id, User user);

    List<AccountDTO> getUserAccounts(User user);

    Boolean deactivateAccount(Long id, User user);
}
