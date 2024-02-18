package com.pay.tracker.category.api;

import com.pay.tracker.category.persistance.TransactionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDTO {
    @Schema(description = "Category Id", example = "1")
    private Long id;

    @Schema(description = "User Id")
    private Long userId;

    @Schema(description = "Category Name", example = "Groceries")
    private String name;

    @Schema(description = "Transaction type")
    private TransactionTypeEnum type;
}
