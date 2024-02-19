package com.pay.tracker.category.api;

import com.pay.tracker.category.persistance.TransactionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class CategoryResponseDTO {
    @Schema(description = "Category Id", example = "1")
    private Long id;

    @Schema(description = "Category Name", example = "Groceries")
    private String name;

    @Schema(description = "Transaction type")
    private TransactionTypeEnum type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryResponseDTO that = (CategoryResponseDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
