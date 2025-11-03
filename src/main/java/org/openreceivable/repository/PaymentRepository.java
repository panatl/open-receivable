package org.openreceivable.repository;

import org.openreceivable.enums.PaymentStatus;
import org.openreceivable.model.Payment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Payment entity
 */
public interface PaymentRepository {
    
    /**
     * Save a payment
     */
    Payment save(Payment payment);
    
    /**
     * Find payment by ID
     */
    Optional<Payment> findById(String paymentId);
    
    /**
     * Find all payments for a customer
     */
    List<Payment> findByCustomerId(String customerId);
    
    /**
     * Find all payments for a receivable
     */
    List<Payment> findByReceivableId(String receivableId);
    
    /**
     * Find all payments by status
     */
    List<Payment> findByStatus(PaymentStatus status);
    
    /**
     * Find all payments between dates
     */
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find all payments
     */
    List<Payment> findAll();
    
    /**
     * Delete a payment
     */
    void delete(String paymentId);
    
    /**
     * Check if payment exists
     */
    boolean exists(String paymentId);
}
