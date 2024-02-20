package com.pay.tracker.account.persistance;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accounts")
@NoArgsConstructor
public class Account {
    public Account(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private String name;

    @NotNull
    private Long initialBalance;

    @NotNull
    private Boolean active;

}
