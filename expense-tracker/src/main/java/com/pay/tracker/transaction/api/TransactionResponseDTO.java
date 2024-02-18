package com.pay.tracker.transaction.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pay.tracker.account.api.AccountDTO;
import com.pay.tracker.category.api.CategoryResponseDTO;
import com.pay.tracker.category.persistance.TransactionTypeEnum;
import com.pay.tracker.commons.converter.ToFormattedDateTimeConverter;
import com.pay.tracker.commons.converter.ToLocalDateTimeConverter;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponseDTO {
    private Long id;
    private Long userId;
    private AccountDTO account;
    private CategoryResponseDTO category;
    private Long amount;
    private String description;
    @JsonSerialize(using = ToFormattedDateTimeConverter.class)
    @JsonDeserialize(using = ToLocalDateTimeConverter.class)
    private LocalDateTime date;
    private TransactionTypeEnum type;
}
