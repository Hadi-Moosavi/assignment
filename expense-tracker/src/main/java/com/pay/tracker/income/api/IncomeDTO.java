package com.pay.tracker.income.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IncomeDTO {
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
    @Schema(description = "Income value", example = "5000")
    private Long value;

    @Schema(description = "Income description", example = "2", nullable = true)
    private String description;

    @Schema(description = "Transaction date in format of yyyyMMdd HHmmSS", example = "20240218231020", nullable = true)
    private LocalDateTime date;
}
