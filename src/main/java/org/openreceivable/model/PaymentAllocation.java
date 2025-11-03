package org.openreceivable.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * PaymentAllocation entity representing allocation of payments to receivables
 */
public class PaymentAllocation {
    private String allocationId;
    private String paymentId;
    private String receivableId;
    private BigDecimal amount;
    private LocalDateTime allocationDate;
    private BigDecimal principalAmount;
    private BigDecimal interestAmount;
    private BigDecimal feeAmount;

    public PaymentAllocation() {
        this.allocationId = UUID.randomUUID().toString();
        this.allocationDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(String allocationId) {
        this.allocationId = allocationId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getReceivableId() {
        return receivableId;
    }

    public void setReceivableId(String receivableId) {
        this.receivableId = receivableId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getAllocationDate() {
        return allocationDate;
    }

    public void setAllocationDate(LocalDateTime allocationDate) {
        this.allocationDate = allocationDate;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(BigDecimal principalAmount) {
        this.principalAmount = principalAmount;
    }

    public BigDecimal getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    @Override
    public String toString() {
        return "PaymentAllocation{" +
                "allocationId='" + allocationId + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", receivableId='" + receivableId + '\'' +
                ", amount=" + amount +
                ", allocationDate=" + allocationDate +
                '}';
    }
}
