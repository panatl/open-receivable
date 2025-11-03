package org.openreceivable.repository;

import org.openreceivable.model.PaymentAllocation;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for PaymentAllocation entity
 */
public interface PaymentAllocationRepository {
    
    /**
     * Save a payment allocation
     */
    PaymentAllocation save(PaymentAllocation allocation);
    
    /**
     * Find payment allocation by ID
     */
    Optional<PaymentAllocation> findById(String allocationId);
    
    /**
     * Find all allocations for a payment
     */
    List<PaymentAllocation> findByPaymentId(String paymentId);
    
    /**
     * Find all allocations for a receivable
     */
    List<PaymentAllocation> findByReceivableId(String receivableId);
    
    /**
     * Find all payment allocations
     */
    List<PaymentAllocation> findAll();
    
    /**
     * Delete a payment allocation
     */
    void delete(String allocationId);
    
    /**
     * Check if payment allocation exists
     */
    boolean exists(String allocationId);
}
