package com.pay.tracker.account.service;

import com.pay.tracker.account.api.AccountDTO;
import com.pay.tracker.account.persistance.Account;
import com.pay.tracker.account.persistance.AccountRepository;
import com.pay.tracker.commons.model.BusinessException;
import com.pay.tracker.commons.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
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
            if (accountRepository.existsByUserIdAndName(user.getId(), dto.getName())) {
                throw new BusinessException("account.duplicate");
            }
            account = modelMapper.map(dto, Account.class);
            account.setUserId(user.getId());
            account.setActive(true);
        }
        Account savedModel = accountRepository.save(account);
        return modelMapper.map(savedModel, AccountDTO.class);
    }

    private Account findAndUpdateAccount(AccountDTO dto, User user) {
        Account account;
        account = getAndCheckAccount(dto.getId(), user);
        account.setName(dto.getName());
        account.setInitialBalance(dto.getInitialBalance());
        return account;
    }

    @Override
    public AccountDTO getAccount(Long id, User user) {
        return modelMapper.map(getAndCheckAccount(id, user), AccountDTO.class);
    }

    @Override
    public List<AccountDTO> getUserAccounts(User user) {
        return accountRepository.getAccountByUserIdOrderByIdAsc(user.getId()).stream()
                .map(a->modelMapper.map(a,AccountDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public void deactivateAccount(Long id, User user) {
        var account = getAndCheckAccount(id, user);
        account.setActive(false);
        accountRepository.save(account);
    }

    @Override
    public Account getAndCheckAccount(Long id, User user) {
        Account account;
        account = accountRepository.findById(id).orElseThrow(() -> new BusinessException("entity.not.found"));
        if (!user.getId().equals(account.getUserId())) {
            throw new BusinessException("account.user.mismatch");
        }
        if (!account.getActive()) {
            throw new BusinessException("account.in.active");
        }
        return account;
    }
}
