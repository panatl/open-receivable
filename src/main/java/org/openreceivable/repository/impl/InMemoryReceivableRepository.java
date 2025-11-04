package org.openreceivable.repository.impl;

import org.openreceivable.enums.ReceivableStatus;
import org.openreceivable.model.Receivable;
import org.openreceivable.repository.ReceivableRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of ReceivableRepository
 */
@Repository
public class InMemoryReceivableRepository implements ReceivableRepository {
    
    private final Map<String, Receivable> receivables = new ConcurrentHashMap<>();
    
    @Override
    public Receivable save(Receivable receivable) {
        if (receivable.getReceivableId() == null || receivable.getReceivableId().isEmpty()) {
            receivable.setReceivableId(UUID.randomUUID().toString());
        }
        if (receivable.getCreatedDate() == null) {
            receivable.setCreatedDate(LocalDateTime.now());
        }
        receivables.put(receivable.getReceivableId(), receivable);
        return receivable;
    }
    
    @Override
    public Optional<Receivable> findById(String receivableId) {
        return Optional.ofNullable(receivables.get(receivableId));
    }
    
    @Override
    public List<Receivable> findByCustomerId(String customerId) {
        return receivables.values().stream()
                .filter(r -> customerId.equals(r.getCustomerId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Receivable> findByContractId(String contractId) {
        return receivables.values().stream()
                .filter(r -> contractId.equals(r.getContractId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Receivable> findByStatus(ReceivableStatus status) {
        return receivables.values().stream()
                .filter(r -> status.equals(r.getStatus()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Receivable> findOverdue(LocalDateTime asOfDate) {
        return receivables.values().stream()
                .filter(r -> r.getDueDate() != null && r.getDueDate().isBefore(asOfDate) &&
                            !ReceivableStatus.PAID.equals(r.getStatus()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Receivable> findByDueDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return receivables.values().stream()
                .filter(r -> r.getDueDate() != null &&
                            !r.getDueDate().isBefore(startDate) &&
                            !r.getDueDate().isAfter(endDate))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Receivable> findAll() {
        return new ArrayList<>(receivables.values());
    }
    
    @Override
    public void delete(String receivableId) {
        receivables.remove(receivableId);
    }
    
    @Override
    public boolean exists(String receivableId) {
        return receivables.containsKey(receivableId);
    }
}
