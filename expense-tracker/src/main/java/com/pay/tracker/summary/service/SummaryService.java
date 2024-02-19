package com.pay.tracker.summary.service;

import com.pay.tracker.account.api.AccountDTO;
import com.pay.tracker.category.api.CategoryResponseDTO;
import com.pay.tracker.commons.model.User;
import com.pay.tracker.summary.api.SummaryDTO;

import java.time.LocalDateTime;

public interface SummaryService {
    SummaryDTO<CategoryResponseDTO> getSummaryByCategory(LocalDateTime from, LocalDateTime to, Byte type, Long accountId, User user);

    SummaryDTO<AccountDTO> getSummaryByAccount(LocalDateTime from, LocalDateTime to, Byte type, Long categoryId, User user);

    SummaryDTO<String> getSummaryByType(LocalDateTime from, LocalDateTime to, Long categoryId, Long accountId, User user);
}
