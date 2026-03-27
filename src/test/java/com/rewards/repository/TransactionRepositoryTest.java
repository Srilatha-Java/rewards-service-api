package com.rewards.repository;

import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldFetchTransactionsBetweenDates() {

        Customer customer = new Customer();
        customer.setName("Test User");

        customer = customerRepository.save(customer);

        Transaction t1 = new Transaction();
        t1.setCustomer(customer);
        t1.setAmount(BigDecimal.valueOf(120));
        t1.setTransactionDate(LocalDate.now().minusDays(10));

        transactionRepository.save(t1);

        LocalDate start = LocalDate.now().minusMonths(1);
        LocalDate end = LocalDate.now();

        List<Transaction> result =
                transactionRepository.findByCustomerIdAndTransactionDateBetween(
                        customer.getId(),
                        start,
                        end
                );

        assertFalse(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenNoTransactionsFound() {

        LocalDate start = LocalDate.now().minusMonths(1);
        LocalDate end = LocalDate.now();

        List<Transaction> result =
                transactionRepository.findByCustomerIdAndTransactionDateBetween(
                        999L,
                        start,
                        end
                );

        assertTrue(result.isEmpty());
    }
}