package org.openreceivable.repository.impl;

import org.openreceivable.model.PaymentAllocation;
import org.openreceivable.repository.PaymentAllocationRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of PaymentAllocationRepository
 */
@Repository
public class InMemoryPaymentAllocationRepository implements PaymentAllocationRepository {
    
    private final Map<String, PaymentAllocation> allocations = new ConcurrentHashMap<>();
    
    @Override
    public PaymentAllocation save(PaymentAllocation allocation) {
        if (allocation.getAllocationId() == null || allocation.getAllocationId().isEmpty()) {
            allocation.setAllocationId(UUID.randomUUID().toString());
        }
        allocations.put(allocation.getAllocationId(), allocation);
        return allocation;
    }
    
    @Override
    public Optional<PaymentAllocation> findById(String allocationId) {
        return Optional.ofNullable(allocations.get(allocationId));
    }
    
    @Override
    public List<PaymentAllocation> findByPaymentId(String paymentId) {
        return allocations.values().stream()
                .filter(a -> paymentId.equals(a.getPaymentId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PaymentAllocation> findByReceivableId(String receivableId) {
        return allocations.values().stream()
                .filter(a -> receivableId.equals(a.getReceivableId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PaymentAllocation> findAll() {
        return new ArrayList<>(allocations.values());
    }
    
    @Override
    public void delete(String allocationId) {
        allocations.remove(allocationId);
    }
    
    @Override
    public boolean exists(String allocationId) {
        return allocations.containsKey(allocationId);
    }
}
