package com.rewards;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rewards.service.RewardService;

@SpringBootTest
class RewardsApiApplicationTests {

    @Autowired
    private RewardService rewardService;

    @Test
    void contextLoads() {
        assertNotNull(rewardService);
    }
}