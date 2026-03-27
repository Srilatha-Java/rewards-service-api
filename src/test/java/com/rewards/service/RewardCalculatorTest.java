package com.rewards.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardCalculatorTest {

    private final RewardCalculator calculator = new RewardCalculator();

    @Test
    void test120Amount() {
        assertEquals(90, calculator.calculate(BigDecimal.valueOf(120)));
    }

    @Test
    void test75Amount() {
        assertEquals(25, calculator.calculate(BigDecimal.valueOf(75)));
    }

    @Test
    void testBelow50() {
        assertEquals(0, calculator.calculate(BigDecimal.valueOf(40)));
    }

    @Test
    void testExactly50() {
        assertEquals(0, calculator.calculate(BigDecimal.valueOf(50)));
    }

    @Test
    void testExactly100() {
        assertEquals(50, calculator.calculate(BigDecimal.valueOf(100)));
    }

    @Test
    void testNegative() {
        assertEquals(0, calculator.calculate(BigDecimal.valueOf(-10)));
    }

    @Test
    void testNull() {
        assertEquals(0, calculator.calculate(null));
    }
}