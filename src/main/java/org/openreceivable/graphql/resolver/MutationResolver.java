package org.openreceivable.graphql.resolver;

import org.openreceivable.graphql.input.*;
import org.openreceivable.model.Address;
import org.openreceivable.model.Customer;
import org.openreceivable.model.Payment;
import org.openreceivable.model.Receivable;
import org.openreceivable.repository.CustomerRepository;
import org.openreceivable.repository.PaymentRepository;
import org.openreceivable.repository.ReceivableRepository;
import org.openreceivable.service.ReceivableService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Reactive GraphQL Mutation resolver for Open Receivable System
 */
@Controller
public class MutationResolver {
    
    private final CustomerRepository customerRepository;
    private final ReceivableRepository receivableRepository;
    private final PaymentRepository paymentRepository;
    private final ReceivableService receivableService;
    
    public MutationResolver(CustomerRepository customerRepository,
                           ReceivableRepository receivableRepository,
                           PaymentRepository paymentRepository,
                           ReceivableService receivableService) {
        this.customerRepository = customerRepository;
        this.receivableRepository = receivableRepository;
        this.paymentRepository = paymentRepository;
        this.receivableService = receivableService;
    }
    
    // Customer mutations
    
    @MutationMapping
    public Mono<Customer> createCustomer(@Argument CreateCustomerInput input) {
        return Mono.fromCallable(() -> {
            Customer customer = new Customer();
            customer.setCustomerType(input.getCustomerType());
            customer.setFirstName(input.getFirstName());
            customer.setLastName(input.getLastName());
            customer.setBusinessName(input.getBusinessName());
            customer.setEmail(input.getEmail());
            customer.setPhone(input.getPhone());
            
            if (input.getAddress() != null) {
                Address address = new Address();
                address.setStreet1(input.getAddress().getStreet1());
                address.setStreet2(input.getAddress().getStreet2());
                address.setCity(input.getAddress().getCity());
                address.setState(input.getAddress().getState());
                address.setPostalCode(input.getAddress().getPostalCode());
                address.setCountry(input.getAddress().getCountry());
                customer.setAddress(address);
            }
            
            customer.setTaxId(input.getTaxId());
            customer.setCreditScore(input.getCreditScore());
            customer.setStatus(input.getStatus());
            
            return customerRepository.save(customer);
        });
    }
    
    @MutationMapping
    public Mono<Customer> updateCustomer(@Argument UpdateCustomerInput input) {
        return Mono.fromCallable(() -> {
            Customer customer = customerRepository.findById(input.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found: " + input.getCustomerId()));
            
            if (input.getCustomerType() != null) {
                customer.setCustomerType(input.getCustomerType());
            }
            if (input.getFirstName() != null) {
                customer.setFirstName(input.getFirstName());
            }
            if (input.getLastName() != null) {
                customer.setLastName(input.getLastName());
            }
            if (input.getBusinessName() != null) {
                customer.setBusinessName(input.getBusinessName());
            }
            if (input.getEmail() != null) {
                customer.setEmail(input.getEmail());
            }
            if (input.getPhone() != null) {
                customer.setPhone(input.getPhone());
            }
            
            if (input.getAddress() != null) {
                Address address = new Address();
                address.setStreet1(input.getAddress().getStreet1());
                address.setStreet2(input.getAddress().getStreet2());
                address.setCity(input.getAddress().getCity());
                address.setState(input.getAddress().getState());
                address.setPostalCode(input.getAddress().getPostalCode());
                address.setCountry(input.getAddress().getCountry());
                customer.setAddress(address);
            }
            
            if (input.getTaxId() != null) {
                customer.setTaxId(input.getTaxId());
            }
            if (input.getCreditScore() != null) {
                customer.setCreditScore(input.getCreditScore());
            }
            if (input.getStatus() != null) {
                customer.setStatus(input.getStatus());
            }
            
            customer.setModifiedDate(LocalDateTime.now());
            return customerRepository.save(customer);
        });
    }
    
    // Receivable mutations
    
    @MutationMapping
    public Mono<Receivable> createReceivable(@Argument CreateReceivableInput input) {
        return Mono.fromCallable(() -> {
            Receivable receivable = new Receivable();
            receivable.setContractId(input.getContractId());
            receivable.setCustomerId(input.getCustomerId());
            receivable.setReceivableType(input.getReceivableType());
            receivable.setDueDate(input.getDueDate());
            receivable.setOriginalAmount(input.getOriginalAmount());
            receivable.setInvoiceNumber(input.getInvoiceNumber());
            receivable.setDescription(input.getDescription());
            
            return receivableService.createReceivable(receivable);
        });
    }
    
    @MutationMapping
    public Mono<Receivable> updateReceivable(@Argument UpdateReceivableInput input) {
        return Mono.fromCallable(() -> {
            Receivable receivable = receivableRepository.findById(input.getReceivableId())
                    .orElseThrow(() -> new RuntimeException("Receivable not found: " + input.getReceivableId()));
            
            if (input.getStatus() != null) {
                receivable.setStatus(input.getStatus());
            }
            if (input.getPaidAmount() != null) {
                receivable.setPaidAmount(input.getPaidAmount());
                receivable.setOutstandingAmount(
                    receivable.getOriginalAmount().subtract(input.getPaidAmount())
                );
            }
            if (input.getPaidDate() != null) {
                receivable.setPaidDate(input.getPaidDate());
            }
            
            return receivableRepository.save(receivable);
        });
    }
    
    // Payment mutations
    
    @MutationMapping
    public Mono<Payment> createPayment(@Argument CreatePaymentInput input) {
        return Mono.fromCallable(() -> {
            Payment payment = new Payment();
            payment.setCustomerId(input.getCustomerId());
            payment.setPaymentDate(input.getPaymentDate());
            payment.setAmount(input.getAmount());
            payment.setPaymentMethod(input.getPaymentMethod());
            payment.setReferenceNumber(input.getReferenceNumber());
            payment.setNotes(input.getNotes());
            
            return paymentRepository.save(payment);
        });
    }
    
    @MutationMapping
    public Mono<Payment> updatePayment(@Argument UpdatePaymentInput input) {
        return Mono.fromCallable(() -> {
            Payment payment = paymentRepository.findById(input.getPaymentId())
                    .orElseThrow(() -> new RuntimeException("Payment not found: " + input.getPaymentId()));
            
            if (input.getStatus() != null) {
                payment.setStatus(input.getStatus());
            }
            if (input.getNotes() != null) {
                payment.setNotes(input.getNotes());
            }
            
            return paymentRepository.save(payment);
        });
    }
    
    // Aging mutation
    
    @MutationMapping
    public Mono<Boolean> updateAging() {
        return Mono.fromCallable(() -> {
            try {
                receivableService.updateAging();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
}
