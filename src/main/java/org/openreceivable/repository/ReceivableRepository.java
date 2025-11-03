package org.openreceivable.repository;

import org.openreceivable.enums.ReceivableStatus;
import org.openreceivable.model.Receivable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Receivable entity
 */
public interface ReceivableRepository {
    
    /**
     * Save a receivable
     */
    Receivable save(Receivable receivable);
    
    /**
     * Find receivable by ID
     */
    Optional<Receivable> findById(String receivableId);
    
    /**
     * Find all receivables for a customer
     */
    List<Receivable> findByCustomerId(String customerId);
    
    /**
     * Find all receivables for a contract
     */
    List<Receivable> findByContractId(String contractId);
    
    /**
     * Find all receivables by status
     */
    List<Receivable> findByStatus(ReceivableStatus status);
    
    /**
     * Find all overdue receivables
     */
    List<Receivable> findOverdue(LocalDateTime asOfDate);
    
    /**
     * Find all receivables due between dates
     */
    List<Receivable> findByDueDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find all receivables
     */
    List<Receivable> findAll();
    
    /**
     * Delete a receivable
     */
    void delete(String receivableId);
    
    /**
     * Check if receivable exists
     */
    boolean exists(String receivableId);
}
