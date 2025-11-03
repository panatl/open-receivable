package org.openreceivable.service;

import org.openreceivable.enums.ContractStatus;
import org.openreceivable.enums.ReceivableType;
import org.openreceivable.model.Contract;
import org.openreceivable.model.Receivable;
import org.openreceivable.repository.ContractRepository;
import org.openreceivable.repository.ReceivableRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing contracts and generating receivables
 */
public class ContractService {
    
    private final ContractRepository contractRepository;
    private final ReceivableRepository receivableRepository;
    
    public ContractService(ContractRepository contractRepository,
                          ReceivableRepository receivableRepository) {
        this.contractRepository = contractRepository;
        this.receivableRepository = receivableRepository;
    }
    
    /**
     * Create a new contract
     */
    public Contract createContract(Contract contract) {
        contract.setStatus(ContractStatus.ACTIVE);
        Contract savedContract = contractRepository.save(contract);
        
        // Generate initial receivables if down payment is required
        if (contract.getDownPayment() != null && 
            contract.getDownPayment().compareTo(BigDecimal.ZERO) > 0) {
            generateDownPaymentReceivable(savedContract);
        }
        
        return savedContract;
    }
    
    /**
     * Generate down payment receivable
     */
    private void generateDownPaymentReceivable(Contract contract) {
        Receivable receivable = new Receivable();
        receivable.setContractId(contract.getContractId());
        receivable.setCustomerId(contract.getCustomerId());
        receivable.setReceivableType(ReceivableType.DOWN_PAYMENT);
        receivable.setDueDate(contract.getStartDate());
        receivable.setOriginalAmount(contract.getDownPayment());
        receivable.setOutstandingAmount(contract.getDownPayment());
        receivable.setDescription("Down payment for contract " + contract.getContractNumber());
        receivableRepository.save(receivable);
    }
    
    /**
     * Generate monthly payment receivables for a contract
     */
    public List<Receivable> generateMonthlyReceivables(String contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found: " + contractId));
        
        List<Receivable> receivables = new ArrayList<>();
        LocalDateTime dueDate = contract.getStartDate();
        
        for (int i = 0; i < contract.getTerm(); i++) {
            dueDate = dueDate.plusMonths(1);
            
            Receivable receivable = new Receivable();
            receivable.setContractId(contract.getContractId());
            receivable.setCustomerId(contract.getCustomerId());
            receivable.setReceivableType(ReceivableType.MONTHLY_PAYMENT);
            receivable.setDueDate(dueDate);
            receivable.setOriginalAmount(contract.getMonthlyPayment());
            receivable.setOutstandingAmount(contract.getMonthlyPayment());
            receivable.setDescription("Monthly payment " + (i + 1) + " of " + contract.getTerm());
            
            Receivable saved = receivableRepository.save(receivable);
            receivables.add(saved);
        }
        
        return receivables;
    }
    
    /**
     * Generate late fee receivable
     */
    public Receivable generateLateFeeReceivable(String receivableId, BigDecimal feeAmount) {
        Receivable original = receivableRepository.findById(receivableId)
                .orElseThrow(() -> new RuntimeException("Receivable not found: " + receivableId));
        
        Receivable lateFee = new Receivable();
        lateFee.setContractId(original.getContractId());
        lateFee.setCustomerId(original.getCustomerId());
        lateFee.setReceivableType(ReceivableType.LATE_FEE);
        lateFee.setDueDate(LocalDateTime.now());
        lateFee.setOriginalAmount(feeAmount);
        lateFee.setOutstandingAmount(feeAmount);
        lateFee.setDescription("Late fee for invoice " + original.getInvoiceNumber());
        
        return receivableRepository.save(lateFee);
    }
    
    /**
     * Get all contracts for a customer
     */
    public List<Contract> getCustomerContracts(String customerId) {
        return contractRepository.findByCustomerId(customerId);
    }
    
    /**
     * Terminate a contract
     */
    public Contract terminateContract(String contractId, BigDecimal earlyTerminationFee) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found: " + contractId));
        
        contract.setStatus(ContractStatus.TERMINATED);
        contract.setModifiedDate(LocalDateTime.now());
        
        // Generate early termination fee if applicable
        if (earlyTerminationFee != null && earlyTerminationFee.compareTo(BigDecimal.ZERO) > 0) {
            Receivable terminationFee = new Receivable();
            terminationFee.setContractId(contractId);
            terminationFee.setCustomerId(contract.getCustomerId());
            terminationFee.setReceivableType(ReceivableType.EARLY_TERMINATION);
            terminationFee.setDueDate(LocalDateTime.now());
            terminationFee.setOriginalAmount(earlyTerminationFee);
            terminationFee.setOutstandingAmount(earlyTerminationFee);
            terminationFee.setDescription("Early termination fee");
            receivableRepository.save(terminationFee);
        }
        
        return contractRepository.save(contract);
    }
}
