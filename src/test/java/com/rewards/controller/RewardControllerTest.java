package com.rewards.controller;

import com.rewards.dto.RewardResponseDTO;
import com.rewards.service.RewardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardControllerTest {

    @Mock
    private RewardService rewardService;

    @InjectMocks
    private RewardController rewardController;

    /**
     *  Test getRewards()
     */
    @Test
    void shouldReturnRewardsForCustomer() {

        Long customerId = 1L;

        Map<String, Integer> monthlyPoints = Map.of(
                "Jan", 120,
                "Feb", 240
        );

        RewardResponseDTO mockResponse =
                new RewardResponseDTO(customerId, monthlyPoints, 360);

        when(rewardService.getRewards(customerId))
                .thenReturn(mockResponse);

        ResponseEntity<RewardResponseDTO> response =
                rewardController.getRewards(customerId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(customerId, response.getBody().getCustomerId());
        assertEquals(360, response.getBody().getTotalPoints());
        assertEquals(120, response.getBody().getMonthlyPoints().get("Jan"));

        verify(rewardService).getRewards(customerId);
    }
}