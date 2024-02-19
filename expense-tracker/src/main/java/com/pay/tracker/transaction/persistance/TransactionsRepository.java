package com.pay.tracker.transaction.persistance;

import com.pay.tracker.account.persistance.Account;
import com.pay.tracker.category.persistance.Category;
import com.pay.tracker.category.persistance.TransactionTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t join fetch t.category join fetch t.account " +
            "where t.userId=:userId and t.date >=:from and t.date <=:to " +
            "and (:type is null or t.type = :type) and (:categoryId is null or t.category.id = :categoryId) and (:accountId is null or t.account.id = :accountId) " +
            "order by t.date desc")
    List<Transaction> filter(Long userId, LocalDateTime from, LocalDateTime to, TransactionTypeEnum type, Long categoryId, Long accountId);
}
