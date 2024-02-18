package com.pay.tracker.income.persistance;

import com.pay.tracker.account.persistance.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "incomes")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private Long value;
    private LocalDateTime date;

}
