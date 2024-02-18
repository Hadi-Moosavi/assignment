package com.pay.tracker.category.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> getByUserIdAndTypeOrderByName(Long userId, TransactionTypeEnum type);
    boolean existsByUserIdAndName(Long userId, String name);
}
