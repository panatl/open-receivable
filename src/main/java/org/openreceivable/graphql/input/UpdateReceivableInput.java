package org.openreceivable.graphql.input;

import org.openreceivable.enums.ReceivableStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Input type for updating a receivable
 */
public class UpdateReceivableInput {
    private String receivableId;
    private ReceivableStatus status;
    private BigDecimal paidAmount;
    private LocalDateTime paidDate;

    // Getters and Setters
    public String getReceivableId() {
        return receivableId;
    }

    public void setReceivableId(String receivableId) {
        this.receivableId = receivableId;
    }

    public ReceivableStatus getStatus() {
        return status;
    }

    public void setStatus(ReceivableStatus status) {
        this.status = status;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public LocalDateTime getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDateTime paidDate) {
        this.paidDate = paidDate;
    }
}
