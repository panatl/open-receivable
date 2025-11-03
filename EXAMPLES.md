# Example Usage

This document demonstrates how to use the Open Receivable System.

## Creating a Customer

```java
import org.openreceivable.model.*;
import org.openreceivable.enums.*;

// Create a customer
Customer customer = new Customer();
customer.setCustomerType(CustomerType.INDIVIDUAL);
customer.setFirstName("John");
customer.setLastName("Doe");
customer.setEmail("john.doe@example.com");
customer.setPhone("555-0123");
customer.setCreditScore(720);
customer.setStatus(CustomerStatus.ACTIVE);

// Set address
Address address = new Address();
address.setStreet1("123 Main St");
address.setCity("Springfield");
address.setState("IL");
address.setPostalCode("62701");
address.setCountry("US");
customer.setAddress(address);
```

## Creating a Vehicle

```java
import java.math.BigDecimal;

Vehicle vehicle = new Vehicle();
vehicle.setVin("1HGBH41JXMN109186");
vehicle.setMake("Honda");
vehicle.setModel("Accord");
vehicle.setYear(2023);
vehicle.setColor("Silver");
vehicle.setMileage(0);
vehicle.setPurchasePrice(new BigDecimal("28000.00"));
vehicle.setCurrentValue(new BigDecimal("28000.00"));
vehicle.setStatus(VehicleStatus.AVAILABLE);
vehicle.setCondition(VehicleCondition.NEW);
```

## Creating a Lease Contract

```java
import java.time.LocalDateTime;

Contract contract = new Contract();
contract.setCustomerId(customer.getCustomerId());
contract.setVehicleId(vehicle.getVehicleId());
contract.setContractType(ContractType.LEASE);
contract.setContractNumber("LEASE-2024-001");
contract.setStartDate(LocalDateTime.now());
contract.setEndDate(LocalDateTime.now().plusMonths(36));
contract.setPrincipalAmount(new BigDecimal("25000.00"));
contract.setInterestRate(new BigDecimal("4.5"));
contract.setTerm(36);
contract.setPaymentFrequency(PaymentFrequency.MONTHLY);
contract.setMonthlyPayment(new BigDecimal("450.00"));
contract.setDownPayment(new BigDecimal("3000.00"));
contract.setResidualValue(new BigDecimal("15000.00"));
contract.setMileageLimit(12000);
contract.setExcessMileageRate(new BigDecimal("0.25"));
contract.setSecurityDeposit(new BigDecimal("500.00"));

// Create contract and generate receivables
ContractService contractService = new ContractService(contractRepository, receivableRepository);
Contract savedContract = contractService.createContract(contract);
List<Receivable> monthlyReceivables = contractService.generateMonthlyReceivables(savedContract.getContractId());
```

## Creating a Loan Contract

```java
Contract loanContract = new Contract();
loanContract.setCustomerId(customer.getCustomerId());
loanContract.setVehicleId(vehicle.getVehicleId());
loanContract.setContractType(ContractType.LOAN);
loanContract.setContractNumber("LOAN-2024-001");
loanContract.setStartDate(LocalDateTime.now());
loanContract.setEndDate(LocalDateTime.now().plusMonths(60));
loanContract.setPrincipalAmount(new BigDecimal("25000.00"));
loanContract.setInterestRate(new BigDecimal("5.9"));
loanContract.setTerm(60);
loanContract.setPaymentFrequency(PaymentFrequency.MONTHLY);
loanContract.setMonthlyPayment(new BigDecimal("483.00"));
loanContract.setDownPayment(new BigDecimal("3000.00"));
```

## Processing a Payment

```java
// Create a payment
Payment payment = new Payment();
payment.setCustomerId(customer.getCustomerId());
payment.setContractId(contract.getContractId());
payment.setPaymentDate(LocalDateTime.now());
payment.setAmount(new BigDecimal("450.00"));
payment.setPaymentMethod(PaymentMethod.ACH);
payment.setReferenceNumber("ACH-20240115-001");
payment.setStatus(PaymentStatus.CLEARED);

// Get receivables to pay
List<Receivable> receivables = receivableRepository.findByContractId(contract.getContractId());
List<String> receivableIds = receivables.stream()
    .filter(r -> r.getStatus() != ReceivableStatus.PAID)
    .map(Receivable::getReceivableId)
    .collect(Collectors.toList());

// Process payment
PaymentService paymentService = new PaymentService(paymentRepository, receivableRepository, allocationRepository);
Payment processedPayment = paymentService.processPayment(payment, receivableIds);
```

## Generating Aging Report

```java
ReceivableService receivableService = new ReceivableService(receivableRepository, agingBucketRepository);

// Update aging for all receivables
receivableService.updateAging();

// Get aging report for a customer
Map<AgingCategory, BigDecimal> agingReport = receivableService.getCustomerAgingReport(customer.getCustomerId());

System.out.println("Customer Aging Report:");
System.out.println("Current: $" + agingReport.get(AgingCategory.CURRENT));
System.out.println("1-30 days: $" + agingReport.get(AgingCategory.DAYS_1_30));
System.out.println("31-60 days: $" + agingReport.get(AgingCategory.DAYS_31_60));
System.out.println("61-90 days: $" + agingReport.get(AgingCategory.DAYS_61_90));
System.out.println("91-120 days: $" + agingReport.get(AgingCategory.DAYS_91_120));
System.out.println("Over 120 days: $" + agingReport.get(AgingCategory.OVER_120));
```

## Getting Overdue Receivables

```java
List<Receivable> overdueReceivables = receivableService.getOverdueReceivables();

for (Receivable receivable : overdueReceivables) {
    System.out.println("Overdue: " + receivable.getInvoiceNumber() + 
                      " - $" + receivable.getOutstandingAmount() + 
                      " - " + receivable.calculateAgingDays() + " days");
}
```

## Generating Late Fees

```java
BigDecimal lateFeeAmount = new BigDecimal("25.00");
for (Receivable overdue : overdueReceivables) {
    if (overdue.calculateAgingDays() > 15) {
        contractService.generateLateFeeReceivable(overdue.getReceivableId(), lateFeeAmount);
    }
}
```

## Terminating a Contract Early

```java
BigDecimal earlyTerminationFee = new BigDecimal("500.00");
Contract terminatedContract = contractService.terminateContract(contract.getContractId(), earlyTerminationFee);
```

## Getting Customer Balance

```java
BigDecimal outstandingBalance = receivableService.getCustomerOutstandingBalance(customer.getCustomerId());
System.out.println("Customer outstanding balance: $" + outstandingBalance);
```
