package org.openreceivable.model;

import org.openreceivable.enums.ReceivableStatus;
import org.openreceivable.enums.ReceivableType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Receivable entity representing amounts owed
 */
public class Receivable {
    private String receivableId;
    private String contractId;
    private String customerId;
    private ReceivableType receivableType;
    private LocalDateTime dueDate;
    private BigDecimal originalAmount;
    private BigDecimal outstandingAmount;
    private BigDecimal paidAmount;
    private ReceivableStatus status;
    private String invoiceNumber;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime paidDate;
    private Integer agingDays;

    public Receivable() {
        this.receivableId = UUID.randomUUID().toString();
        this.createdDate = LocalDateTime.now();
        this.paidAmount = BigDecimal.ZERO;
    }

    // Getters and Setters
    public String getReceivableId() {
        return receivableId;
    }

    public void setReceivableId(String receivableId) {
        this.receivableId = receivableId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ReceivableType getReceivableType() {
        return receivableType;
    }

    public void setReceivableType(ReceivableType receivableType) {
        this.receivableType = receivableType;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public ReceivableStatus getStatus() {
        return status;
    }

    public void setStatus(ReceivableStatus status) {
        this.status = status;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDateTime paidDate) {
        this.paidDate = paidDate;
    }

    public Integer getAgingDays() {
        return agingDays;
    }

    public void setAgingDays(Integer agingDays) {
        this.agingDays = agingDays;
    }

    /**
     * Calculate aging days based on current date
     */
    public int calculateAgingDays() {
        if (dueDate == null || status == ReceivableStatus.PAID) {
            return 0;
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(dueDate)) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(dueDate, now);
    }

    /**
     * Check if receivable is overdue
     */
    public boolean isOverdue() {
        return calculateAgingDays() > 0 && status != ReceivableStatus.PAID;
    }

    @Override
    public String toString() {
        return "Receivable{" +
                "receivableId='" + receivableId + '\'' +
                ", contractId='" + contractId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", receivableType=" + receivableType +
                ", dueDate=" + dueDate +
                ", originalAmount=" + originalAmount +
                ", outstandingAmount=" + outstandingAmount +
                ", status=" + status +
                '}';
    }
}
