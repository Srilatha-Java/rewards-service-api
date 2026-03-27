package com.rewards.service;

import com.rewards.dto.RewardResponseDTO;

import java.util.List;

/**
 * Service interface for reward calculation operations.
 */
public interface RewardService {

    /**
     * Calculates reward points for a specific customer.
     *
     * @param customerId the customer ID
     * @return reward response DTO containing monthly and total points
     */
    RewardResponseDTO getRewards(Long customerId);
}

