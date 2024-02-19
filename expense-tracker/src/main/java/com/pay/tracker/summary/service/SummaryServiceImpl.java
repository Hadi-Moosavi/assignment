package com.pay.tracker.summary.service;

import com.pay.tracker.account.api.AccountDTO;
import com.pay.tracker.category.api.CategoryResponseDTO;
import com.pay.tracker.commons.model.User;
import com.pay.tracker.summary.api.SummaryDTO;
import com.pay.tracker.transaction.api.TransactionResponseDTO;
import com.pay.tracker.transaction.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.pay.tracker.category.persistance.TransactionTypeEnum.INCOME;

@Service
public class SummaryServiceImpl implements SummaryService {

    private final TransactionService transactionService;

    public SummaryServiceImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public SummaryDTO<CategoryResponseDTO> getSummaryByCategory(LocalDateTime from, LocalDateTime to, Byte type, Long accountId, User user) {
        var filterRes = transactionService.filter(from, to, type, null, accountId, user);
        var grouped = filterRes.stream().collect(Collectors.groupingBy(TransactionResponseDTO::getCategory));
        var list = grouped.entrySet().stream().map(e ->
                new SummaryDTO.Row<>(e.getKey(),
                        e.getValue().size(),
                        e.getValue().stream().mapToLong(TransactionResponseDTO::getAmount).sum())).toList();

        var result = new SummaryDTO<CategoryResponseDTO>();
        result.setList(list);
        result.setTotalCount(list.stream().mapToInt(SummaryDTO.Row::getCount).sum());
        result.setTotalBalance(list.stream().mapToLong(r -> r.getItem().getType() == INCOME ? r.getBalance() : -r.getBalance()).sum());
        return result;
    }

    @Override
    public SummaryDTO<AccountDTO> getSummaryByAccount(LocalDateTime from, LocalDateTime to, Byte type, Long categoryId, User user) {
        var filterRes = transactionService.filter(from, to, type, categoryId, null, user);
        var grouped = filterRes.stream().collect(Collectors.groupingBy(TransactionResponseDTO::getAccount));
        var list = grouped.entrySet().stream().map(e ->
                new SummaryDTO.Row<>(e.getKey(),
                        e.getValue().size(),
                        e.getValue().stream().mapToLong(t -> t.getType() == INCOME ? t.getAmount() : -t.getAmount()).sum())).toList();

        var result = new SummaryDTO<AccountDTO>();
        result.setList(list);
        result.setTotalCount(list.stream().mapToInt(SummaryDTO.Row::getCount).sum());
        result.setTotalBalance(list.stream().mapToLong(SummaryDTO.Row::getBalance).sum());
        return result;
    }

    @Override
    public SummaryDTO<String> getSummaryByType(LocalDateTime from, LocalDateTime to, Long categoryId, Long accountId, User user) {
        var filterRes = transactionService.filter(from, to, null, categoryId, accountId, user);
        var grouped = filterRes.stream().collect(Collectors.groupingBy(TransactionResponseDTO::getType));
        var list = grouped.entrySet().stream().map(e ->
                new SummaryDTO.Row<>(e.getKey().name(),
                        e.getValue().size(),
                        e.getValue().stream().mapToLong(TransactionResponseDTO::getAmount).sum())).toList();

        var result = new SummaryDTO<String>();
        result.setList(list);
        result.setTotalCount(list.stream().mapToInt(SummaryDTO.Row::getCount).sum());
        result.setTotalBalance(list.stream().mapToLong(r -> r.getItem().equals(INCOME.name()) ? r.getBalance() : -r.getBalance()).sum());
        return result;
    }
}
