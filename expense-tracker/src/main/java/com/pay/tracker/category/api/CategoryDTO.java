package com.pay.tracker.category.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    @Schema(description = "Category Id, required for update", example = "1", nullable = true)
    private Long id;

    @Schema(hidden = true, nullable = true)
    private Long userId;

    @NotNull
    @Schema(description = "Category Name", example = "Groceries")
    private String name;

    @Schema(description = "Transaction type, 1: income, 2: expense", example = "1", minimum = "1", maximum = "2")
    @Min(1)
    @Min(2)
    @NotNull
    private Byte transactionTypeCode;
}
