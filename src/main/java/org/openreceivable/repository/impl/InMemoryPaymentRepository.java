package org.openreceivable.repository.impl;

import org.openreceivable.enums.PaymentStatus;
import org.openreceivable.model.Payment;
import org.openreceivable.repository.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of PaymentRepository
 */
@Repository
public class InMemoryPaymentRepository implements PaymentRepository {
    
    private final Map<String, Payment> payments = new ConcurrentHashMap<>();
    
    @Override
    public Payment save(Payment payment) {
        if (payment.getPaymentId() == null || payment.getPaymentId().isEmpty()) {
            payment.setPaymentId(UUID.randomUUID().toString());
        }
        if (payment.getCreatedDate() == null) {
            payment.setCreatedDate(LocalDateTime.now());
        }
        payments.put(payment.getPaymentId(), payment);
        return payment;
    }
    
    @Override
    public Optional<Payment> findById(String paymentId) {
        return Optional.ofNullable(payments.get(paymentId));
    }
    
    @Override
    public List<Payment> findByCustomerId(String customerId) {
        return payments.values().stream()
                .filter(p -> customerId.equals(p.getCustomerId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Payment> findByReceivableId(String receivableId) {
        return payments.values().stream()
                .filter(p -> receivableId.equals(p.getReceivableId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Payment> findByStatus(PaymentStatus status) {
        return payments.values().stream()
                .filter(p -> status.equals(p.getStatus()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return payments.values().stream()
                .filter(p -> p.getPaymentDate() != null &&
                            !p.getPaymentDate().isBefore(startDate) &&
                            !p.getPaymentDate().isAfter(endDate))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Payment> findAll() {
        return new ArrayList<>(payments.values());
    }
    
    @Override
    public void delete(String paymentId) {
        payments.remove(paymentId);
    }
    
    @Override
    public boolean exists(String paymentId) {
        return payments.containsKey(paymentId);
    }
}
