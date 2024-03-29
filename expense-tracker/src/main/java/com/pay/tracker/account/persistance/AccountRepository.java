package com.pay.tracker.account.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> getAccountByUserIdOrderByIdAsc(Long userId);

    boolean existsByUserIdAndName(Long userId, String name);
}
