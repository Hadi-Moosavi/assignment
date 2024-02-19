package com.pay.tracker.account.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class AccountDTO {
    @Schema(description = "Account Id, required for update", example = "1", nullable = true)
    private Long id;

    @Schema(hidden = true, nullable = true)
    private Long userId;

    @NotNull
    @Schema(description = "Account Name", example = "Blue Bank Account")
    private String name;

    @Schema(description = "Account Initial Balance", example = "100000", minimum = "0")
    @NotNull
    @Min(0)
    private Long initialBalance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
