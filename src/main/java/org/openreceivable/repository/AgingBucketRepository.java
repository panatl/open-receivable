package org.openreceivable.repository;

import org.openreceivable.enums.AgingCategory;
import org.openreceivable.model.AgingBucket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for AgingBucket entity
 */
public interface AgingBucketRepository {
    
    /**
     * Save an aging bucket
     */
    AgingBucket save(AgingBucket bucket);
    
    /**
     * Find aging bucket by ID
     */
    Optional<AgingBucket> findById(String bucketId);
    
    /**
     * Find all aging buckets for a customer
     */
    List<AgingBucket> findByCustomerId(String customerId);
    
    /**
     * Find all aging buckets for a receivable
     */
    List<AgingBucket> findByReceivableId(String receivableId);
    
    /**
     * Find all aging buckets by category
     */
    List<AgingBucket> findByAgingCategory(AgingCategory category);
    
    /**
     * Find all aging buckets as of a specific date
     */
    List<AgingBucket> findByAsOfDate(LocalDateTime asOfDate);
    
    /**
     * Find all aging buckets
     */
    List<AgingBucket> findAll();
    
    /**
     * Delete an aging bucket
     */
    void delete(String bucketId);
    
    /**
     * Check if aging bucket exists
     */
    boolean exists(String bucketId);
}
