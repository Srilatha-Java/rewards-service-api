package com.rewards.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO representing reward details of a customer.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardResponseDTO {

    /**
     * Unique ID of the customer.
     */
    private Long customerId;

    /**
     * Monthly reward points grouped by month.
     */
    private Map<String, Integer> monthlyPoints;

    /**
     * Total reward points for last 3 months.
     */
    private Integer totalPoints;
}