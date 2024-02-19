package com.pay.tracker.transaction.service;

import com.pay.tracker.account.service.AccountService;
import com.pay.tracker.category.persistance.Category;
import com.pay.tracker.category.persistance.TransactionTypeEnum;
import com.pay.tracker.category.service.CategoryService;
import com.pay.tracker.commons.model.BusinessException;
import com.pay.tracker.commons.model.User;
import com.pay.tracker.transaction.api.TransactionDTO;
import com.pay.tracker.transaction.api.TransactionResponseDTO;
import com.pay.tracker.transaction.persistance.Transaction;
import com.pay.tracker.transaction.persistance.TransactionsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionsRepository transactionsRepository;
    private final ModelMapper modelMapper;
    private final AccountService accountService;
    private final CategoryService categoryService;

    public TransactionServiceImpl(TransactionsRepository transactionsRepository, ModelMapper modelMapper, AccountService accountService, CategoryService categoryService) {
        this.transactionsRepository = transactionsRepository;
        this.modelMapper = modelMapper;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public TransactionResponseDTO saveTransaction(TransactionDTO dto, User user) {
        var income = buildModel(dto, user);
        var savedModel = transactionsRepository.save(income);
        return modelMapper.map(savedModel, TransactionResponseDTO.class);
    }

    private Transaction buildModel(TransactionDTO dto, User user) {
        Transaction transaction;
        if (dto.getId() != null) {
            transaction = getAndCheckTransaction(dto.getId(), user);
        } else {
            transaction = new Transaction();
            transaction.setUserId(user.getId());
        }
        transaction.setType(TransactionTypeEnum.getByCode(dto.getTransactionTypeCode()));
        Category category = categoryService.getAndCheckCategory(dto.getCategoryId(), user);
        if (category.getType() != transaction.getType()) {
            throw new BusinessException("Selected category type does not match transaction type");
        }
        transaction.setCategory(category);
        transaction.setAccount(accountService.getAndCheckAccount(dto.getAccountId(), user));
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount());
        transaction.setDate(dto.getDate() == null ? LocalDateTime.now() : dto.getDate());
        return transaction;
    }

    @Override
    public TransactionResponseDTO getTransaction(Long id, User user) {
        return modelMapper.map(getAndCheckTransaction(id, user), TransactionResponseDTO.class);
    }

    @Override
    public List<TransactionResponseDTO> filter(LocalDateTime from, LocalDateTime to, Byte typeCode, Long categoryId, User user) {
        var type = getAndCheckTransType(typeCode);
        var res = transactionsRepository.filter(user.getId(), new Category(categoryId), type, from, to);
        return res.stream()
                .map(a -> modelMapper.map(a, TransactionResponseDTO.class))
                .toList();
    }

    private static TransactionTypeEnum getAndCheckTransType(Byte typeCode) {
        var type = TransactionTypeEnum.getByCode(typeCode);
        if (type == null) {
            throw new BusinessException("Invalid typeCode");
        }
        return type;
    }

    private Transaction getAndCheckTransaction(Long id, User user) {
        Transaction transaction;
        transaction = transactionsRepository.findById(id).orElseThrow(() -> new BusinessException("Entity not found."));
        if (!user.getId().equals(transaction.getUserId())) {
            throw new BusinessException("Transaction not belong to user");
        }
        return transaction;
    }
}
