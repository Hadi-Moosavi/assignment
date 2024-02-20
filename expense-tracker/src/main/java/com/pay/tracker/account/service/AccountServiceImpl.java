package com.pay.tracker.account.service;

import com.pay.tracker.account.api.AccountDTO;
import com.pay.tracker.account.api.AccountBalanceDTO;
import com.pay.tracker.account.persistance.Account;
import com.pay.tracker.account.persistance.AccountRepository;
import com.pay.tracker.commons.model.BusinessException;
import com.pay.tracker.commons.model.User;
import com.pay.tracker.transaction.persistance.Transaction;
import com.pay.tracker.transaction.persistance.TransactionsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pay.tracker.category.persistance.TransactionTypeEnum.INCOME;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionsRepository transactionsRepository;
    private final ModelMapper modelMapper;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionsRepository transactionsRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.transactionsRepository = transactionsRepository;
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
    public AccountBalanceDTO getAccountBalance(Long id, User user) {
        var account = getAndCheckAccount(id, user);
        return new AccountBalanceDTO(account.getId(), account.getName(),
                account.getInitialBalance() + getTotalBalance(id));
    }

    private Long getTotalBalance(Long accId) {
        List<Transaction> trans = transactionsRepository.getByAccount_Id(accId);
        return trans.stream().mapToLong(t -> t.getType() == INCOME ? t.getAmount() : -t.getAmount()).sum();
    }

    @Override
    public List<AccountDTO> getUserAccounts(User user) {
        return accountRepository.getAccountByUserIdOrderByIdAsc(user.getId()).stream()
                .map(a -> modelMapper.map(a, AccountDTO.class))
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
