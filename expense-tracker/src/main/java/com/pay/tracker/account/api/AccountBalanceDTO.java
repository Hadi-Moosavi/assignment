package com.pay.tracker.account.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceDTO {
    @Schema(description = "Account Id, required for update", example = "1", nullable = true)
    private Long id;

    @Schema(description = "Account Name", example = "Blue Bank Account")
    private String name;

    @Schema(description = "Account Current Balance", example = "100000")
    private Long currentBalance;
}
