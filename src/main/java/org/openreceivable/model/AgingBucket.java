package org.openreceivable.model;

import org.openreceivable.enums.AgingCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * AgingBucket entity for categorizing receivables by age
 */
public class AgingBucket {
    private String bucketId;
    private String receivableId;
    private String customerId;
    private AgingCategory agingCategory;
    private BigDecimal amount;
    private LocalDateTime asOfDate;
    private LocalDateTime createdDate;

    public AgingBucket() {
        this.bucketId = UUID.randomUUID().toString();
        this.createdDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getReceivableId() {
        return receivableId;
    }

    public void setReceivableId(String receivableId) {
        this.receivableId = receivableId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public AgingCategory getAgingCategory() {
        return agingCategory;
    }

    public void setAgingCategory(AgingCategory agingCategory) {
        this.agingCategory = agingCategory;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(LocalDateTime asOfDate) {
        this.asOfDate = asOfDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Determine aging category based on aging days
     */
    public static AgingCategory determineCategory(int agingDays) {
        if (agingDays <= 0) {
            return AgingCategory.CURRENT;
        } else if (agingDays <= 30) {
            return AgingCategory.DAYS_1_30;
        } else if (agingDays <= 60) {
            return AgingCategory.DAYS_31_60;
        } else if (agingDays <= 90) {
            return AgingCategory.DAYS_61_90;
        } else if (agingDays <= 120) {
            return AgingCategory.DAYS_91_120;
        } else {
            return AgingCategory.OVER_120;
        }
    }

    @Override
    public String toString() {
        return "AgingBucket{" +
                "bucketId='" + bucketId + '\'' +
                ", receivableId='" + receivableId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", agingCategory=" + agingCategory +
                ", amount=" + amount +
                ", asOfDate=" + asOfDate +
                '}';
    }
}
