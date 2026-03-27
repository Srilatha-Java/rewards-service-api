package com.rewards.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Integration tests for Reward APIs.
 *
 * This test loads full Spring Boot context and verifies
 * end-to-end request flow including controller, service,
 * repository and database layers.
 */
@SpringBootTest
@AutoConfigureMockMvc
class RewardIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test: Valid customer with transactions
     */
    @Test
    void shouldReturnRewardsForValidCustomer() throws Exception {

        mockMvc.perform(get("/api/rewards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.totalPoints").exists())
                .andExpect(jsonPath("$.monthlyPoints").exists());
    }

    /**
     * Test: Invalid customer → should return 404
     */
    @Test
    void shouldReturn404ForInvalidCustomer() throws Exception {

        mockMvc.perform(get("/api/rewards/9999"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test Get Rewards for all customers
     */
    @Test
    void shouldReturnRewardsForAllCustomers() throws Exception {

        mockMvc.perform(get("/api/rewards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].customerId").exists())
                .andExpect(jsonPath("$[0].monthlyPoints").exists())
                .andExpect(jsonPath("$[0].totalPoints").exists());
    }
}