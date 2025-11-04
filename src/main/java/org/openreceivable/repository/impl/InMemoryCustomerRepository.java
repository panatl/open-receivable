package org.openreceivable.repository.impl;

import org.openreceivable.enums.CustomerStatus;
import org.openreceivable.model.Customer;
import org.openreceivable.repository.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of CustomerRepository
 */
@Repository
public class InMemoryCustomerRepository implements CustomerRepository {
    
    private final Map<String, Customer> customers = new ConcurrentHashMap<>();
    
    @Override
    public Customer save(Customer customer) {
        if (customer.getCustomerId() == null || customer.getCustomerId().isEmpty()) {
            customer.setCustomerId(UUID.randomUUID().toString());
        }
        if (customer.getCreatedDate() == null) {
            customer.setCreatedDate(LocalDateTime.now());
        }
        customer.setModifiedDate(LocalDateTime.now());
        customers.put(customer.getCustomerId(), customer);
        return customer;
    }
    
    @Override
    public Optional<Customer> findById(String customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }
    
    @Override
    public Optional<Customer> findByEmail(String email) {
        return customers.values().stream()
                .filter(c -> email.equals(c.getEmail()))
                .findFirst();
    }
    
    @Override
    public Optional<Customer> findByTaxId(String taxId) {
        return customers.values().stream()
                .filter(c -> taxId.equals(c.getTaxId()))
                .findFirst();
    }
    
    @Override
    public List<Customer> findByStatus(CustomerStatus status) {
        return customers.values().stream()
                .filter(c -> status.equals(c.getStatus()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }
    
    @Override
    public void delete(String customerId) {
        customers.remove(customerId);
    }
    
    @Override
    public boolean exists(String customerId) {
        return customers.containsKey(customerId);
    }
}
