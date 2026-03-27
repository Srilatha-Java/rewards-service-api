package com.rewards.repository;

import com.rewards.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Customer entity.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
