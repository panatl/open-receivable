package org.openreceivable.repository;

import org.openreceivable.enums.ContractStatus;
import org.openreceivable.model.Contract;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Contract entity
 */
public interface ContractRepository {
    
    /**
     * Save a contract
     */
    Contract save(Contract contract);
    
    /**
     * Find contract by ID
     */
    Optional<Contract> findById(String contractId);
    
    /**
     * Find contract by contract number
     */
    Optional<Contract> findByContractNumber(String contractNumber);
    
    /**
     * Find all contracts for a customer
     */
    List<Contract> findByCustomerId(String customerId);
    
    /**
     * Find all contracts for a vehicle
     */
    List<Contract> findByVehicleId(String vehicleId);
    
    /**
     * Find all contracts by status
     */
    List<Contract> findByStatus(ContractStatus status);
    
    /**
     * Find all contracts
     */
    List<Contract> findAll();
    
    /**
     * Delete a contract
     */
    void delete(String contractId);
    
    /**
     * Check if contract exists
     */
    boolean exists(String contractId);
}
