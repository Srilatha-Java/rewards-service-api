package com.rewards.repository;

import com.rewards.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for Transaction entity.
 * Provides database operations for transactions.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Fetches transactions of a customer within a date range.
     *
     * @param customerId the customer ID
     * @param start start date (inclusive)
     * @param end end date (inclusive)
     * @return list of transactions
     */
    List<Transaction> findByCustomerIdAndTransactionDateBetween(
            Long customerId,
            LocalDate start,
            LocalDate end
    );
}