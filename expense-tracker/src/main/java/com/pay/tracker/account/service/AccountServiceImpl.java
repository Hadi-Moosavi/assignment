package com.pay.tracker.account.service;

import com.pay.tracker.account.api.AccountDTO;
import com.pay.tracker.account.persistance.Account;
import com.pay.tracker.account.persistance.AccountRepository;
import com.pay.tracker.commons.model.ErrorException;
import com.pay.tracker.commons.model.User;
import io.swagger.v3.oas.annotations.servers.Server;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Server
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public AccountDTO saveAccount(AccountDTO dto, User user) {
        Account account;
        if (dto.getId() != null) {
            account = findAndUpdateAccount(dto, user);
        } else {
            account = modelMapper.map(dto, Account.class);
            account.setUserId(user.getId());
            account.setActive(true);
        }
        Account savedModel = accountRepository.save(account);
        return modelMapper.map(savedModel, AccountDTO.class);
    }

    private Account findAndUpdateAccount(AccountDTO dto, User user) {
        Account account;
        account = accountRepository.findById(dto.getId()).orElseThrow(() -> new ErrorException("Entity not found."));
        if (!user.getId().equals(account.getUserId())) {
            throw new ErrorException("Account not belong to user");
        }
        if (!account.getActive()) {
            throw new ErrorException("Account is not active");
        }
        account.setName(dto.getName());
        account.setInitialBalance(dto.getInitialBalance());
        return account;
    }

    @Override
    public AccountDTO getAccount(Long id, User user) {
        return null;
    }

    @Override
    public List<AccountDTO> getUserAccounts(User user) {
        return null;
    }

    @Override
    public Boolean deactivateAccount(Long id, User user) {
        return null;
    }
}
