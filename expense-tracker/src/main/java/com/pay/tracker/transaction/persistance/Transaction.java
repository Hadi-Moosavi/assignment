package com.pay.tracker.transaction.persistance;

import com.pay.tracker.account.persistance.Account;
import com.pay.tracker.category.persistance.Category;
import com.pay.tracker.category.persistance.TransactionTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "incomes")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private Long amount;
    private String description;
    private LocalDateTime date;
    private TransactionTypeEnum type;
}
