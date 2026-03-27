package com.rewards.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Utility class responsible for calculating reward points
 * based on transaction amount.
 */
@Component
public class RewardCalculator {

    /**
     * Calculates reward points based on business rules:
     * - 2 points for every dollar spent above 100
     * - 1 point for every dollar spent between 50 and 100
     *
     * @param amount transaction amount
     * @return calculated reward points
     */
    public int calculate(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.valueOf(50)) <= 0) {
            return 0;
        }

        int points = 0;

        if (amount.compareTo(BigDecimal.valueOf(100)) > 0) {
            points += amount.subtract(BigDecimal.valueOf(100))
                    .multiply(BigDecimal.valueOf(2))
                    .intValue();

            points += 50;
        } else {
            points += amount.subtract(BigDecimal.valueOf(50)).intValue();
        }

        return points;
    }
}