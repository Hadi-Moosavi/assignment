package com.pay.tracker.income.persistance;

import com.pay.tracker.category.persistance.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUserIdAndDateBetween(Long userId, LocalDateTime from, LocalDateTime to);

    List<Income> findByUserIdAndCategoryAndDateBetween(Long userId, Category category, LocalDateTime from, LocalDateTime to);
}
