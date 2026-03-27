package com.rewards.exception;

/**
 * Exception thrown when a requested customer
 * is not found in the system.
 *
 * This is a custom runtime exception used in the
 * Rewards API to indicate that the provided
 * customer ID does not exist.
 */
public class CustomerNotFoundException extends RuntimeException {

    /**
     * Constructs a new CustomerNotFoundException
     * with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
