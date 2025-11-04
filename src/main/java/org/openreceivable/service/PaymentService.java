package org.openreceivable.service;

import org.openreceivable.enums.ReceivableStatus;
import org.openreceivable.model.Payment;
import org.openreceivable.model.PaymentAllocation;
import org.openreceivable.model.Receivable;
import org.openreceivable.repository.PaymentAllocationRepository;
import org.openreceivable.repository.PaymentRepository;
import org.openreceivable.repository.ReceivableRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for processing payments and allocating them to receivables
 */
@Service
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final ReceivableRepository receivableRepository;
    private final PaymentAllocationRepository allocationRepository;
    
    public PaymentService(PaymentRepository paymentRepository, 
                         ReceivableRepository receivableRepository,
                         PaymentAllocationRepository allocationRepository) {
        this.paymentRepository = paymentRepository;
        this.receivableRepository = receivableRepository;
        this.allocationRepository = allocationRepository;
    }
    
    /**
     * Process a payment and allocate it to receivables
     * Follows the priority: Fees -> Interest -> Principal
     */
    public Payment processPayment(Payment payment, List<String> receivableIds) {
        // Save the payment
        Payment savedPayment = paymentRepository.save(payment);
        
        BigDecimal remainingAmount = payment.getAmount();
        
        // Allocate payment to each receivable in order
        for (String receivableId : receivableIds) {
            if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
            
            Receivable receivable = receivableRepository.findById(receivableId)
                    .orElseThrow(() -> new RuntimeException("Receivable not found: " + receivableId));
            
            BigDecimal amountToApply = remainingAmount.min(receivable.getOutstandingAmount());
            
            // Create allocation
            PaymentAllocation allocation = new PaymentAllocation();
            allocation.setPaymentId(savedPayment.getPaymentId());
            allocation.setReceivableId(receivableId);
            allocation.setAmount(amountToApply);
            allocation.setAllocationDate(LocalDateTime.now());
            
            // For simplicity, apply all to principal
            // In a real system, you'd split between fees, interest, and principal
            allocation.setPrincipalAmount(amountToApply);
            allocation.setInterestAmount(BigDecimal.ZERO);
            allocation.setFeeAmount(BigDecimal.ZERO);
            
            allocationRepository.save(allocation);
            
            // Update receivable
            BigDecimal newPaidAmount = receivable.getPaidAmount().add(amountToApply);
            BigDecimal newOutstanding = receivable.getOutstandingAmount().subtract(amountToApply);
            
            receivable.setPaidAmount(newPaidAmount);
            receivable.setOutstandingAmount(newOutstanding);
            
            if (newOutstanding.compareTo(BigDecimal.ZERO) == 0) {
                receivable.setStatus(ReceivableStatus.PAID);
                receivable.setPaidDate(LocalDateTime.now());
            } else if (newOutstanding.compareTo(receivable.getOriginalAmount()) < 0) {
                receivable.setStatus(ReceivableStatus.PARTIAL);
            }
            
            receivableRepository.save(receivable);
            
            remainingAmount = remainingAmount.subtract(amountToApply);
        }
        
        return savedPayment;
    }
    
    /**
     * Get all payments for a customer
     */
    public List<Payment> getCustomerPayments(String customerId) {
        return paymentRepository.findByCustomerId(customerId);
    }
    
    /**
     * Get payment allocations for a payment
     */
    public List<PaymentAllocation> getPaymentAllocations(String paymentId) {
        return allocationRepository.findByPaymentId(paymentId);
    }
}
