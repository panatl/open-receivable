package org.openreceivable.graphql.resolver;

import org.openreceivable.enums.CustomerStatus;
import org.openreceivable.graphql.model.AgingReport;
import org.openreceivable.model.Customer;
import org.openreceivable.model.Payment;
import org.openreceivable.model.Receivable;
import org.openreceivable.repository.CustomerRepository;
import org.openreceivable.repository.PaymentRepository;
import org.openreceivable.repository.ReceivableRepository;
import org.openreceivable.service.ReceivableService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Reactive GraphQL Query resolver for Open Receivable System
 */
@Controller
public class QueryResolver {
    
    private final CustomerRepository customerRepository;
    private final ReceivableRepository receivableRepository;
    private final PaymentRepository paymentRepository;
    private final ReceivableService receivableService;
    
    public QueryResolver(CustomerRepository customerRepository,
                        ReceivableRepository receivableRepository,
                        PaymentRepository paymentRepository,
                        ReceivableService receivableService) {
        this.customerRepository = customerRepository;
        this.receivableRepository = receivableRepository;
        this.paymentRepository = paymentRepository;
        this.receivableService = receivableService;
    }
    
    // Customer queries
    
    @QueryMapping
    public Mono<Customer> customer(@Argument String customerId) {
        return Mono.fromCallable(() -> customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + customerId)));
    }
    
    @QueryMapping
    public Flux<Customer> customers(@Argument CustomerStatus status) {
        return Mono.fromCallable(() -> {
            if (status != null) {
                return customerRepository.findByStatus(status);
            }
            return customerRepository.findAll();
        }).flatMapMany(Flux::fromIterable);
    }
    
    // Receivable queries
    
    @QueryMapping
    public Mono<Receivable> receivable(@Argument String receivableId) {
        return Mono.fromCallable(() -> receivableRepository.findById(receivableId)
                .orElseThrow(() -> new RuntimeException("Receivable not found: " + receivableId)));
    }
    
    @QueryMapping
    public Flux<Receivable> receivablesByCustomer(@Argument String customerId) {
        return Mono.fromCallable(() -> receivableService.getCustomerReceivables(customerId))
                .flatMapMany(Flux::fromIterable);
    }
    
    @QueryMapping
    public Flux<Receivable> receivablesByContract(@Argument String contractId) {
        return Mono.fromCallable(() -> receivableService.getContractReceivables(contractId))
                .flatMapMany(Flux::fromIterable);
    }
    
    @QueryMapping
    public Flux<Receivable> overdueReceivables() {
        return Mono.fromCallable(() -> receivableService.getOverdueReceivables())
                .flatMapMany(Flux::fromIterable);
    }
    
    // Payment queries
    
    @QueryMapping
    public Mono<Payment> payment(@Argument String paymentId) {
        return Mono.fromCallable(() -> paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + paymentId)));
    }
    
    @QueryMapping
    public Flux<Payment> paymentsByCustomer(@Argument String customerId) {
        return Mono.fromCallable(() -> paymentRepository.findByCustomerId(customerId))
                .flatMapMany(Flux::fromIterable);
    }
    
    // Aging queries
    
    @QueryMapping
    public Mono<AgingReport> customerAgingReport(@Argument String customerId) {
        return Mono.fromCallable(() -> 
                convertToAgingReport(customerId, receivableService.getCustomerAgingReport(customerId)));
    }
    
    @QueryMapping
    public Mono<BigDecimal> customerOutstandingBalance(@Argument String customerId) {
        return Mono.fromCallable(() -> receivableService.getCustomerOutstandingBalance(customerId));
    }
    
    /**
     * Convert aging category map to AgingReport
     */
    private AgingReport convertToAgingReport(String customerId, Map<org.openreceivable.enums.AgingCategory, BigDecimal> agingMap) {
        return new AgingReport(
            customerId,
            agingMap.getOrDefault(org.openreceivable.enums.AgingCategory.CURRENT, BigDecimal.ZERO),
            agingMap.getOrDefault(org.openreceivable.enums.AgingCategory.DAYS_1_30, BigDecimal.ZERO),
            agingMap.getOrDefault(org.openreceivable.enums.AgingCategory.DAYS_31_60, BigDecimal.ZERO),
            agingMap.getOrDefault(org.openreceivable.enums.AgingCategory.DAYS_61_90, BigDecimal.ZERO),
            agingMap.getOrDefault(org.openreceivable.enums.AgingCategory.DAYS_91_120, BigDecimal.ZERO),
            agingMap.getOrDefault(org.openreceivable.enums.AgingCategory.OVER_120, BigDecimal.ZERO),
            receivableService.getCustomerOutstandingBalance(customerId)
        );
    }
}
