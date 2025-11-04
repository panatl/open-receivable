package org.openreceivable.repository.impl;

import org.openreceivable.enums.AgingCategory;
import org.openreceivable.model.AgingBucket;
import org.openreceivable.repository.AgingBucketRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of AgingBucketRepository
 */
@Repository
public class InMemoryAgingBucketRepository implements AgingBucketRepository {
    
    private final Map<String, AgingBucket> buckets = new ConcurrentHashMap<>();
    
    @Override
    public AgingBucket save(AgingBucket bucket) {
        if (bucket.getBucketId() == null || bucket.getBucketId().isEmpty()) {
            bucket.setBucketId(UUID.randomUUID().toString());
        }
        if (bucket.getCreatedDate() == null) {
            bucket.setCreatedDate(LocalDateTime.now());
        }
        buckets.put(bucket.getBucketId(), bucket);
        return bucket;
    }
    
    @Override
    public Optional<AgingBucket> findById(String bucketId) {
        return Optional.ofNullable(buckets.get(bucketId));
    }
    
    @Override
    public List<AgingBucket> findByCustomerId(String customerId) {
        return buckets.values().stream()
                .filter(b -> customerId.equals(b.getCustomerId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AgingBucket> findByReceivableId(String receivableId) {
        return buckets.values().stream()
                .filter(b -> receivableId.equals(b.getReceivableId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AgingBucket> findByAgingCategory(AgingCategory category) {
        return buckets.values().stream()
                .filter(b -> category.equals(b.getAgingCategory()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AgingBucket> findByAsOfDate(LocalDateTime asOfDate) {
        return buckets.values().stream()
                .filter(b -> asOfDate.equals(b.getAsOfDate()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AgingBucket> findAll() {
        return new ArrayList<>(buckets.values());
    }
    
    @Override
    public void delete(String bucketId) {
        buckets.remove(bucketId);
    }
    
    @Override
    public boolean exists(String bucketId) {
        return buckets.containsKey(bucketId);
    }
}
