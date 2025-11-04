package org.openreceivable.repository.impl;

import org.openreceivable.enums.ContractStatus;
import org.openreceivable.model.Contract;
import org.openreceivable.repository.ContractRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of ContractRepository
 */
@Repository
public class InMemoryContractRepository implements ContractRepository {
    
    private final Map<String, Contract> contracts = new ConcurrentHashMap<>();
    
    @Override
    public Contract save(Contract contract) {
        if (contract.getContractId() == null || contract.getContractId().isEmpty()) {
            contract.setContractId(UUID.randomUUID().toString());
        }
        if (contract.getCreatedDate() == null) {
            contract.setCreatedDate(LocalDateTime.now());
        }
        contract.setModifiedDate(LocalDateTime.now());
        contracts.put(contract.getContractId(), contract);
        return contract;
    }
    
    @Override
    public Optional<Contract> findById(String contractId) {
        return Optional.ofNullable(contracts.get(contractId));
    }
    
    @Override
    public Optional<Contract> findByContractNumber(String contractNumber) {
        return contracts.values().stream()
                .filter(c -> contractNumber.equals(c.getContractNumber()))
                .findFirst();
    }
    
    @Override
    public List<Contract> findByCustomerId(String customerId) {
        return contracts.values().stream()
                .filter(c -> customerId.equals(c.getCustomerId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Contract> findByVehicleId(String vehicleId) {
        return contracts.values().stream()
                .filter(c -> vehicleId.equals(c.getVehicleId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Contract> findByStatus(ContractStatus status) {
        return contracts.values().stream()
                .filter(c -> status.equals(c.getStatus()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Contract> findAll() {
        return new ArrayList<>(contracts.values());
    }
    
    @Override
    public void delete(String contractId) {
        contracts.remove(contractId);
    }
    
    @Override
    public boolean exists(String contractId) {
        return contracts.containsKey(contractId);
    }
}
