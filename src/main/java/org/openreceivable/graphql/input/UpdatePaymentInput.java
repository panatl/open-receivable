package org.openreceivable.graphql.input;

import org.openreceivable.enums.PaymentStatus;

/**
 * Input type for updating a payment
 */
public class UpdatePaymentInput {
    private String paymentId;
    private PaymentStatus status;
    private String notes;

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
