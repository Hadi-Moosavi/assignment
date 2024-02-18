package com.pay.tracker.transaction.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pay.tracker.commons.converter.ToFormattedDateTimeConverter;
import com.pay.tracker.commons.converter.ToLocalDateTimeConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDTO {
    @Schema(description = "Income Id, required for update", example = "1", nullable = true)
    private Long id;

    @Schema(hidden = true, nullable = true)
    private Long userId;

    @NotNull
    @Schema(description = "Id of account involved in transaction", example = "2")
    private Long accountId;

    @NotNull
    @Schema(description = "Id of income category", example = "3")
    private Long categoryId;

    @NotNull
    @Schema(description = "Income amount", example = "5000")
    private Long amount;

    @Schema(description = "Income description", example = "2", nullable = true)
    private String description;

    @JsonSerialize(using = ToFormattedDateTimeConverter.class)
    @JsonDeserialize(using = ToLocalDateTimeConverter.class)
    @Schema(description = "Transaction date in format of yyyy-MM-dd HH:mm:SS", example = "20240218231020", nullable = true)
    private LocalDateTime date;

    @Schema(description = "Transaction type, 1: income, 2: expense", example = "1", minimum = "1", maximum = "2")
    @Min(1)
    @Min(2)
    @NotNull
    private Byte transactionTypeCode;
}
