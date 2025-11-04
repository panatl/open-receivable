package org.openreceivable.service;

import org.openreceivable.enums.AgingCategory;
import org.openreceivable.enums.ReceivableStatus;
import org.openreceivable.model.AgingBucket;
import org.openreceivable.model.Receivable;
import org.openreceivable.repository.AgingBucketRepository;
import org.openreceivable.repository.ReceivableRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for managing receivables and aging analysis
 */
@Service
public class ReceivableService {
    
    private final ReceivableRepository receivableRepository;
    private final AgingBucketRepository agingBucketRepository;
    
    public ReceivableService(ReceivableRepository receivableRepository,
                            AgingBucketRepository agingBucketRepository) {
        this.receivableRepository = receivableRepository;
        this.agingBucketRepository = agingBucketRepository;
    }
    
    /**
     * Create a new receivable
     */
    public Receivable createReceivable(Receivable receivable) {
        receivable.setOutstandingAmount(receivable.getOriginalAmount());
        receivable.setPaidAmount(BigDecimal.ZERO);
        receivable.setStatus(ReceivableStatus.PENDING);
        return receivableRepository.save(receivable);
    }
    
    /**
     * Get all receivables for a customer
     */
    public List<Receivable> getCustomerReceivables(String customerId) {
        return receivableRepository.findByCustomerId(customerId);
    }
    
    /**
     * Get all receivables for a contract
     */
    public List<Receivable> getContractReceivables(String contractId) {
        return receivableRepository.findByContractId(contractId);
    }
    
    /**
     * Get overdue receivables
     */
    public List<Receivable> getOverdueReceivables() {
        return receivableRepository.findOverdue(LocalDateTime.now());
    }
    
    /**
     * Update aging for all receivables
     */
    public void updateAging() {
        List<Receivable> receivables = receivableRepository.findAll();
        LocalDateTime asOfDate = LocalDateTime.now();
        
        for (Receivable receivable : receivables) {
            if (receivable.getStatus() != ReceivableStatus.PAID) {
                int agingDays = receivable.calculateAgingDays();
                receivable.setAgingDays(agingDays);
                
                if (agingDays > 0) {
                    receivable.setStatus(ReceivableStatus.OVERDUE);
                }
                
                receivableRepository.save(receivable);
                
                // Create or update aging bucket
                AgingCategory category = AgingBucket.determineCategory(agingDays);
                AgingBucket bucket = new AgingBucket();
                bucket.setReceivableId(receivable.getReceivableId());
                bucket.setCustomerId(receivable.getCustomerId());
                bucket.setAgingCategory(category);
                bucket.setAmount(receivable.getOutstandingAmount());
                bucket.setAsOfDate(asOfDate);
                
                agingBucketRepository.save(bucket);
            }
        }
    }
    
    /**
     * Get aging report for a customer
     */
    public Map<AgingCategory, BigDecimal> getCustomerAgingReport(String customerId) {
        List<AgingBucket> buckets = agingBucketRepository.findByCustomerId(customerId);
        Map<AgingCategory, BigDecimal> report = new HashMap<>();
        
        for (AgingCategory category : AgingCategory.values()) {
            report.put(category, BigDecimal.ZERO);
        }
        
        for (AgingBucket bucket : buckets) {
            BigDecimal currentAmount = report.get(bucket.getAgingCategory());
            report.put(bucket.getAgingCategory(), currentAmount.add(bucket.getAmount()));
        }
        
        return report;
    }
    
    /**
     * Get total outstanding balance for a customer
     */
    public BigDecimal getCustomerOutstandingBalance(String customerId) {
        List<Receivable> receivables = receivableRepository.findByCustomerId(customerId);
        return receivables.stream()
                .map(Receivable::getOutstandingAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
