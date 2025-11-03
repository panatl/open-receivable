package org.openreceivable.repository;

import org.openreceivable.enums.CustomerStatus;
import org.openreceivable.model.Customer;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Customer entity
 */
public interface CustomerRepository {
    
    /**
     * Save a customer
     */
    Customer save(Customer customer);
    
    /**
     * Find customer by ID
     */
    Optional<Customer> findById(String customerId);
    
    /**
     * Find customer by email
     */
    Optional<Customer> findByEmail(String email);
    
    /**
     * Find customer by tax ID
     */
    Optional<Customer> findByTaxId(String taxId);
    
    /**
     * Find all customers by status
     */
    List<Customer> findByStatus(CustomerStatus status);
    
    /**
     * Find all customers
     */
    List<Customer> findAll();
    
    /**
     * Delete a customer
     */
    void delete(String customerId);
    
    /**
     * Check if customer exists
     */
    boolean exists(String customerId);
}
