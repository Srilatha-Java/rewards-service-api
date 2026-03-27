package com.rewards.service.impl;

import com.rewards.dto.RewardResponseDTO;
import com.rewards.entity.Transaction;
import com.rewards.exception.CustomerNotFoundException;
import com.rewards.repository.CustomerRepository;
import com.rewards.repository.TransactionRepository;
import com.rewards.service.RewardCalculator;
import com.rewards.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class responsible for reward calculations.
 */
@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final RewardCalculator rewardCalculator;

    @Override
    public RewardResponseDTO getRewards(Long customerId) {

        customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found: " + customerId));

        LocalDate start = LocalDate.now().minusMonths(3).withDayOfMonth(1);
        LocalDate end = LocalDate.now();

        List<Transaction> transactions =
                transactionRepository.findByCustomerIdAndTransactionDateBetween(
                        customerId, start, end
                );

        if (transactions.isEmpty()) {
            return new RewardResponseDTO(customerId, Map.of(), 0);
        }

        Map<String, Integer> monthlyPoints =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                t -> t.getTransactionDate().getMonth().toString(),
                                Collectors.summingInt(
                                        t -> rewardCalculator.calculate(t.getAmount())
                                )
                        ));

        int totalPoints = monthlyPoints.values()
                .stream()
                .reduce(0, Integer::sum);

        return new RewardResponseDTO(customerId, monthlyPoints, totalPoints);
    }

}