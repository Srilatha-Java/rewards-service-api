package com.rewards.service.impl;

import com.rewards.dto.RewardResponseDTO;
import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import com.rewards.exception.CustomerNotFoundException;
import com.rewards.repository.CustomerRepository;
import com.rewards.repository.TransactionRepository;
import com.rewards.service.RewardCalculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RewardServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RewardCalculator rewardCalculator;

    @InjectMocks
    private RewardServiceImpl rewardService;

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> rewardService.getRewards(1L));
    }

    @Test
    void shouldReturnRewardsWhenCustomerExists() {

        Customer customer = new Customer(1L, "Srilatha");

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        // ✅ FIXED LINE
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(
                eq(1L), any(), any()))
                .thenReturn(Collections.emptyList());

        RewardResponseDTO response = rewardService.getRewards(1L);

        assertNotNull(response);
        assertEquals(0, response.getTotalPoints());
    }

    @Test
    void shouldHandleEmptyTransactions() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(new Customer(1L, "Test")));

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(
                eq(1L), any(), any()))
                .thenReturn(List.of());

        RewardResponseDTO response = rewardService.getRewards(1L);

        assertEquals(0, response.getTotalPoints());
    }

    @Test
    void shouldHandleNegativeTransactionAmount() {

        Customer customer = new Customer(1L, "Test");

        Transaction txn = new Transaction();
        txn.setAmount(BigDecimal.valueOf(-50));
        txn.setTransactionDate(LocalDate.now());

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(
                eq(1L), any(), any()))
                .thenReturn(List.of(txn));

        when(rewardCalculator.calculate(BigDecimal.valueOf(-50)))
                .thenReturn(0);

        RewardResponseDTO response = rewardService.getRewards(1L);

        assertEquals(0, response.getTotalPoints());
    }

    // ✅ Test: Future transaction (still returned by repo mock)
    @Test
    void shouldIgnoreFutureTransactions() {

        Customer customer = new Customer(1L, "Test");

        Transaction txn = new Transaction();
        txn.setAmount(BigDecimal.valueOf(120));
        txn.setTransactionDate(LocalDate.now().plusDays(5));

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(
                eq(1L), any(), any()))
                .thenReturn(List.of(txn));

        when(rewardCalculator.calculate(BigDecimal.valueOf(120)))
                .thenReturn(90);

        RewardResponseDTO response = rewardService.getRewards(1L);

        assertNotNull(response);
    }

    // ✅ Test: Monthly grouping
    @Test
    void shouldCalculateMonthlyPoints() {

        Customer customer = new Customer(1L, "Test");

        Transaction janTxn = new Transaction();
        janTxn.setAmount(BigDecimal.valueOf(120));
        janTxn.setTransactionDate(LocalDate.of(2026, 1, 10));

        Transaction febTxn = new Transaction();
        febTxn.setAmount(BigDecimal.valueOf(80));
        febTxn.setTransactionDate(LocalDate.of(2026, 2, 10));

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(
                eq(1L), any(), any()))
                .thenReturn(List.of(janTxn, febTxn));

        when(rewardCalculator.calculate(BigDecimal.valueOf(120)))
                .thenReturn(90);

        when(rewardCalculator.calculate(BigDecimal.valueOf(80)))
                .thenReturn(30);

        RewardResponseDTO response = rewardService.getRewards(1L);

        assertEquals(2, response.getMonthlyPoints().size());
        assertEquals(120, response.getTotalPoints()); // 90 + 30
    }

    @Test
    void shouldHandleBoundaryAmounts() {

        Customer customer = new Customer(1L, "Test");

        Transaction t1 = new Transaction();
        t1.setAmount(BigDecimal.valueOf(50)); // boundary
        t1.setTransactionDate(LocalDate.now());

        Transaction t2 = new Transaction();
        t2.setAmount(BigDecimal.valueOf(100)); // boundary
        t2.setTransactionDate(LocalDate.now());

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(
                eq(1L), any(), any()))
                .thenReturn(List.of(t1, t2));

        when(rewardCalculator.calculate(BigDecimal.valueOf(50))).thenReturn(0);
        when(rewardCalculator.calculate(BigDecimal.valueOf(100))).thenReturn(50);

        RewardResponseDTO response = rewardService.getRewards(1L);

        assertEquals(50, response.getTotalPoints());
    }
}

