package com.pay.tracker.income.api;

import com.pay.tracker.account.persistance.Account;
import com.pay.tracker.category.persistance.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IncomeResponseDTO {
    private Long id;
    private Long userId;
    private Account account;
    private Category category;
    private Long value;
    private String description;
    private LocalDateTime date;

}
