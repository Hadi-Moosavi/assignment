package com.pay.tracker.transaction.service;

import com.pay.tracker.commons.model.User;
import com.pay.tracker.transaction.api.TransactionDTO;
import com.pay.tracker.transaction.api.TransactionResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    TransactionResponseDTO saveTransaction(TransactionDTO dto, User user);

    TransactionResponseDTO getTransaction(Long id, User user);

    List<TransactionResponseDTO> filter(LocalDateTime from, LocalDateTime to, Byte type, Long categoryId, Long accountId, User user);
}
