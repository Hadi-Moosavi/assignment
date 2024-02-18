package com.pay.tracker.transaction.persistance;

import com.pay.tracker.category.persistance.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserIdAndDateBetween(Long userId, LocalDateTime from, LocalDateTime to);

    List<Transaction> findByUserIdAndCategoryAndDateBetween(Long userId, Category category, LocalDateTime from, LocalDateTime to);
}
