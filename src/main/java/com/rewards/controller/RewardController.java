package com.rewards.controller;

import com.rewards.dto.RewardResponseDTO;
import com.rewards.service.RewardService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * REST controller for reward endpoints.
 */
@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
@Validated
public class RewardController {

    private final RewardService rewardService;

    /**
     * Fetch reward details for a specific customer.
     *
     * @param customerId the unique ID of the customer
     * @return reward details including monthly and total points
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<RewardResponseDTO> getRewards(
            @PathVariable @NotNull Long customerId) {

        return ResponseEntity.ok(rewardService.getRewards(customerId));
    }
}

