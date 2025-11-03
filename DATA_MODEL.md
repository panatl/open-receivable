# Receivable System Data Model

## Overview
This document defines the data model for a receivable system designed for auto load and lease organizations. The system manages financial transactions related to vehicle leasing and auto loans.

## Core Entities

### 1. Customer
Represents individuals or organizations that lease vehicles or take auto loans.

**Attributes:**
- `customerId` (String, UUID): Primary identifier
- `customerType` (Enum): INDIVIDUAL, BUSINESS
- `firstName` (String): Customer's first name
- `lastName` (String): Customer's last name
- `businessName` (String): For business customers
- `email` (String): Contact email
- `phone` (String): Contact phone
- `address` (Address): Physical address
- `taxId` (String): SSN or EIN
- `creditScore` (Integer): Credit rating
- `status` (Enum): ACTIVE, SUSPENDED, CLOSED
- `createdDate` (DateTime): Account creation date
- `modifiedDate` (DateTime): Last modification date

### 2. Vehicle
Represents vehicles in the lease or loan portfolio.

**Attributes:**
- `vehicleId` (String, UUID): Primary identifier
- `vin` (String): Vehicle Identification Number
- `make` (String): Vehicle manufacturer
- `model` (String): Vehicle model
- `year` (Integer): Manufacturing year
- `color` (String): Vehicle color
- `mileage` (Integer): Current mileage
- `purchasePrice` (Decimal): Original purchase price
- `currentValue` (Decimal): Current market value
- `status` (Enum): AVAILABLE, LEASED, SOLD, MAINTENANCE
- `condition` (Enum): NEW, EXCELLENT, GOOD, FAIR, POOR
- `acquisitionDate` (DateTime): Date acquired
- `licensePlate` (String): License plate number

### 3. Contract
Represents lease or loan agreements.

**Attributes:**
- `contractId` (String, UUID): Primary identifier
- `customerId` (String, UUID): Reference to Customer
- `vehicleId` (String, UUID): Reference to Vehicle
- `contractType` (Enum): LEASE, LOAN
- `contractNumber` (String): Human-readable contract number
- `startDate` (DateTime): Contract start date
- `endDate` (DateTime): Contract end date
- `status` (Enum): ACTIVE, COMPLETED, DEFAULTED, TERMINATED
- `principalAmount` (Decimal): Total amount financed
- `interestRate` (Decimal): Annual interest rate percentage
- `term` (Integer): Contract term in months
- `paymentFrequency` (Enum): MONTHLY, BI_WEEKLY, WEEKLY
- `monthlyPayment` (Decimal): Regular payment amount
- `downPayment` (Decimal): Initial down payment
- `residualValue` (Decimal): For leases - end of term value
- `mileageLimit` (Integer): For leases - annual mileage limit
- `excessMileageRate` (Decimal): Charge per mile over limit
- `securityDeposit` (Decimal): Security deposit amount
- `createdDate` (DateTime): Contract creation date
- `modifiedDate` (DateTime): Last modification date

### 4. Receivable
Represents amounts owed by customers.

**Attributes:**
- `receivableId` (String, UUID): Primary identifier
- `contractId` (String, UUID): Reference to Contract
- `customerId` (String, UUID): Reference to Customer
- `receivableType` (Enum): MONTHLY_PAYMENT, DOWN_PAYMENT, LATE_FEE, EXCESS_MILEAGE, DAMAGE_FEE, EARLY_TERMINATION, OTHER
- `dueDate` (DateTime): Payment due date
- `originalAmount` (Decimal): Original amount due
- `outstandingAmount` (Decimal): Remaining unpaid amount
- `paidAmount` (Decimal): Amount paid so far
- `status` (Enum): PENDING, PAID, PARTIAL, OVERDUE, WRITTEN_OFF
- `invoiceNumber` (String): Invoice reference number
- `description` (String): Description of charge
- `createdDate` (DateTime): Receivable creation date
- `paidDate` (DateTime): Date fully paid
- `agingDays` (Integer): Days past due

### 5. Payment
Represents payments made by customers.

**Attributes:**
- `paymentId` (String, UUID): Primary identifier
- `receivableId` (String, UUID): Reference to Receivable
- `customerId` (String, UUID): Reference to Customer
- `contractId` (String, UUID): Reference to Contract
- `paymentDate` (DateTime): Date payment received
- `amount` (Decimal): Payment amount
- `paymentMethod` (Enum): CASH, CHECK, ACH, CREDIT_CARD, DEBIT_CARD, WIRE_TRANSFER
- `referenceNumber` (String): Transaction reference
- `status` (Enum): PENDING, CLEARED, FAILED, REVERSED
- `processedBy` (String): User who processed payment
- `notes` (String): Additional notes
- `createdDate` (DateTime): Payment record creation date

### 6. PaymentAllocation
Represents how a payment is allocated across receivables.

**Attributes:**
- `allocationId` (String, UUID): Primary identifier
- `paymentId` (String, UUID): Reference to Payment
- `receivableId` (String, UUID): Reference to Receivable
- `amount` (Decimal): Allocated amount
- `allocationDate` (DateTime): Date of allocation
- `principalAmount` (Decimal): Amount applied to principal
- `interestAmount` (Decimal): Amount applied to interest
- `feeAmount` (Decimal): Amount applied to fees

### 7. AgingBucket
Categorizes receivables by aging for reporting.

**Attributes:**
- `bucketId` (String, UUID): Primary identifier
- `receivableId` (String, UUID): Reference to Receivable
- `customerId` (String, UUID): Reference to Customer
- `agingCategory` (Enum): CURRENT, DAYS_1_30, DAYS_31_60, DAYS_61_90, DAYS_91_120, OVER_120
- `amount` (Decimal): Outstanding amount
- `asOfDate` (DateTime): Snapshot date
- `createdDate` (DateTime): Record creation date

### 8. Address (Embedded Object)
**Attributes:**
- `street1` (String): Street address line 1
- `street2` (String): Street address line 2
- `city` (String): City
- `state` (String): State/Province
- `postalCode` (String): ZIP/Postal code
- `country` (String): Country

## Relationships

- Customer → Contract (One-to-Many): A customer can have multiple contracts
- Vehicle → Contract (One-to-Many): A vehicle can be under multiple contracts over time
- Contract → Receivable (One-to-Many): A contract generates multiple receivables
- Customer → Receivable (One-to-Many): A customer has multiple receivables
- Receivable → Payment (Many-to-Many via PaymentAllocation): Payments can be split across receivables
- Payment → PaymentAllocation (One-to-Many): A payment can be allocated to multiple receivables
- Receivable → AgingBucket (One-to-One): Each receivable has an aging classification

## Key Business Rules

1. **Payment Application Priority**: Payments should be applied in the order: Fees → Interest → Principal
2. **Aging Calculation**: Calculate aging based on days past due date
3. **Late Fees**: Automatically generate late fee receivables for overdue payments
4. **Contract Lifecycle**: Track contract status from active through completion or default
5. **Payment Allocation**: A single payment can be split across multiple receivables
6. **Write-off Policy**: Receivables over 120 days may be eligible for write-off
7. **Lease End**: Generate excess mileage and damage receivables at lease termination

## Indexes for Performance

- Customer: `customerId`, `email`, `taxId`
- Vehicle: `vehicleId`, `vin`
- Contract: `contractId`, `customerId`, `vehicleId`, `status`
- Receivable: `receivableId`, `contractId`, `customerId`, `status`, `dueDate`
- Payment: `paymentId`, `receivableId`, `customerId`, `paymentDate`
- AgingBucket: `receivableId`, `customerId`, `agingCategory`, `asOfDate`
