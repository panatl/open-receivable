package org.openreceivable.graphql.input;

import org.openreceivable.enums.ReceivableType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Input type for creating a receivable
 */
public class CreateReceivableInput {
    private String contractId;
    private String customerId;
    private ReceivableType receivableType;
    private LocalDateTime dueDate;
    private BigDecimal originalAmount;
    private String invoiceNumber;
    private String description;

    // Getters and Setters
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
}
