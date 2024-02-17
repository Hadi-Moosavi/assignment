package com.pay.tracker.account.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
    private Long id;
    private Long userId;
    @NotNull
    private String name;
    @Min(0)
    private Long initialBalance;
}
