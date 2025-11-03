# JSON Document Examples

This document provides example JSON documents based on the defined schemas.

## Customer Document Example

```json
{
  "customerId": "550e8400-e29b-41d4-a716-446655440001",
  "customerType": "INDIVIDUAL",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "+1-555-0123",
  "address": {
    "street1": "123 Main Street",
    "street2": "Apt 4B",
    "city": "Springfield",
    "state": "IL",
    "postalCode": "62701",
    "country": "US"
  },
  "taxId": "123-45-6789",
  "creditScore": 720,
  "status": "ACTIVE",
  "createdDate": "2024-01-15T10:30:00Z",
  "modifiedDate": "2024-01-15T10:30:00Z"
}
```

## Business Customer Example

```json
{
  "customerId": "550e8400-e29b-41d4-a716-446655440002",
  "customerType": "BUSINESS",
  "businessName": "ABC Delivery Services LLC",
  "email": "accounts@abcdelivery.com",
  "phone": "+1-555-0199",
  "address": {
    "street1": "500 Corporate Drive",
    "city": "Chicago",
    "state": "IL",
    "postalCode": "60601",
    "country": "US"
  },
  "taxId": "12-3456789",
  "creditScore": 750,
  "status": "ACTIVE",
  "createdDate": "2024-01-10T09:00:00Z",
  "modifiedDate": "2024-01-10T09:00:00Z"
}
```

## Vehicle Document Example

```json
{
  "vehicleId": "650e8400-e29b-41d4-a716-446655440001",
  "vin": "1HGBH41JXMN109186",
  "make": "Honda",
  "model": "Accord",
  "year": 2023,
  "color": "Silver",
  "mileage": 15000,
  "purchasePrice": 28000.00,
  "currentValue": 26500.00,
  "status": "LEASED",
  "condition": "EXCELLENT",
  "acquisitionDate": "2023-06-15T14:00:00Z",
  "licensePlate": "ABC-1234"
}
```

## Lease Contract Example

```json
{
  "contractId": "750e8400-e29b-41d4-a716-446655440001",
  "customerId": "550e8400-e29b-41d4-a716-446655440001",
  "vehicleId": "650e8400-e29b-41d4-a716-446655440001",
  "contractType": "LEASE",
  "contractNumber": "LEASE-2024-001",
  "startDate": "2024-01-15T00:00:00Z",
  "endDate": "2027-01-15T00:00:00Z",
  "status": "ACTIVE",
  "principalAmount": 25000.00,
  "interestRate": 4.5,
  "term": 36,
  "paymentFrequency": "MONTHLY",
  "monthlyPayment": 450.00,
  "downPayment": 3000.00,
  "residualValue": 15000.00,
  "mileageLimit": 12000,
  "excessMileageRate": 0.25,
  "securityDeposit": 500.00,
  "createdDate": "2024-01-15T10:30:00Z",
  "modifiedDate": "2024-01-15T10:30:00Z"
}
```

## Loan Contract Example

```json
{
  "contractId": "750e8400-e29b-41d4-a716-446655440002",
  "customerId": "550e8400-e29b-41d4-a716-446655440001",
  "vehicleId": "650e8400-e29b-41d4-a716-446655440002",
  "contractType": "LOAN",
  "contractNumber": "LOAN-2024-001",
  "startDate": "2024-01-20T00:00:00Z",
  "endDate": "2029-01-20T00:00:00Z",
  "status": "ACTIVE",
  "principalAmount": 25000.00,
  "interestRate": 5.9,
  "term": 60,
  "paymentFrequency": "MONTHLY",
  "monthlyPayment": 483.00,
  "downPayment": 3000.00,
  "createdDate": "2024-01-20T11:00:00Z",
  "modifiedDate": "2024-01-20T11:00:00Z"
}
```

## Receivable Document Example - Monthly Payment

```json
{
  "receivableId": "850e8400-e29b-41d4-a716-446655440001",
  "contractId": "750e8400-e29b-41d4-a716-446655440001",
  "customerId": "550e8400-e29b-41d4-a716-446655440001",
  "receivableType": "MONTHLY_PAYMENT",
  "dueDate": "2024-02-15T00:00:00Z",
  "originalAmount": 450.00,
  "outstandingAmount": 450.00,
  "paidAmount": 0.00,
  "status": "PENDING",
  "invoiceNumber": "INV-2024-001-001",
  "description": "Monthly payment 1 of 36",
  "createdDate": "2024-01-15T10:30:00Z",
  "agingDays": 0
}
```

## Receivable Document Example - Late Fee

```json
{
  "receivableId": "850e8400-e29b-41d4-a716-446655440010",
  "contractId": "750e8400-e29b-41d4-a716-446655440001",
  "customerId": "550e8400-e29b-41d4-a716-446655440001",
  "receivableType": "LATE_FEE",
  "dueDate": "2024-03-20T00:00:00Z",
  "originalAmount": 25.00,
  "outstandingAmount": 25.00,
  "paidAmount": 0.00,
  "status": "PENDING",
  "invoiceNumber": "LATE-2024-001-001",
  "description": "Late fee for invoice INV-2024-001-002",
  "createdDate": "2024-03-20T08:00:00Z",
  "agingDays": 0
}
```

## Payment Document Example

```json
{
  "paymentId": "950e8400-e29b-41d4-a716-446655440001",
  "receivableId": "850e8400-e29b-41d4-a716-446655440001",
  "customerId": "550e8400-e29b-41d4-a716-446655440001",
  "contractId": "750e8400-e29b-41d4-a716-446655440001",
  "paymentDate": "2024-02-14T15:30:00Z",
  "amount": 450.00,
  "paymentMethod": "ACH",
  "referenceNumber": "ACH-20240214-12345",
  "status": "CLEARED",
  "processedBy": "system",
  "notes": "Automatic payment via ACH",
  "createdDate": "2024-02-14T15:30:00Z"
}
```

## Payment Allocation Example

```json
{
  "allocationId": "a50e8400-e29b-41d4-a716-446655440001",
  "paymentId": "950e8400-e29b-41d4-a716-446655440001",
  "receivableId": "850e8400-e29b-41d4-a716-446655440001",
  "amount": 450.00,
  "allocationDate": "2024-02-14T15:30:00Z",
  "principalAmount": 390.00,
  "interestAmount": 60.00,
  "feeAmount": 0.00
}
```

## Aging Bucket Example

```json
{
  "bucketId": "b50e8400-e29b-41d4-a716-446655440001",
  "receivableId": "850e8400-e29b-41d4-a716-446655440005",
  "customerId": "550e8400-e29b-41d4-a716-446655440001",
  "agingCategory": "DAYS_31_60",
  "amount": 450.00,
  "asOfDate": "2024-04-20T00:00:00Z",
  "createdDate": "2024-04-20T01:00:00Z"
}
```

## Complete Transaction Flow Example

### 1. Customer Creates Contract
```json
{
  "customer": {
    "customerId": "550e8400-e29b-41d4-a716-446655440001",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "status": "ACTIVE"
  },
  "vehicle": {
    "vehicleId": "650e8400-e29b-41d4-a716-446655440001",
    "vin": "1HGBH41JXMN109186",
    "make": "Honda",
    "model": "Accord",
    "year": 2023
  },
  "contract": {
    "contractId": "750e8400-e29b-41d4-a716-446655440001",
    "contractType": "LEASE",
    "term": 36,
    "monthlyPayment": 450.00
  }
}
```

### 2. System Generates Receivables
```json
[
  {
    "receivableId": "850e8400-e29b-41d4-a716-446655440001",
    "receivableType": "DOWN_PAYMENT",
    "dueDate": "2024-01-15T00:00:00Z",
    "originalAmount": 3000.00
  },
  {
    "receivableId": "850e8400-e29b-41d4-a716-446655440002",
    "receivableType": "MONTHLY_PAYMENT",
    "dueDate": "2024-02-15T00:00:00Z",
    "originalAmount": 450.00
  },
  {
    "receivableId": "850e8400-e29b-41d4-a716-446655440003",
    "receivableType": "MONTHLY_PAYMENT",
    "dueDate": "2024-03-15T00:00:00Z",
    "originalAmount": 450.00
  }
]
```

### 3. Customer Makes Payment
```json
{
  "payment": {
    "paymentId": "950e8400-e29b-41d4-a716-446655440001",
    "amount": 3450.00,
    "paymentMethod": "CHECK",
    "paymentDate": "2024-01-15T10:00:00Z"
  },
  "allocations": [
    {
      "receivableId": "850e8400-e29b-41d4-a716-446655440001",
      "amount": 3000.00
    },
    {
      "receivableId": "850e8400-e29b-41d4-a716-446655440002",
      "amount": 450.00
    }
  ]
}
```

## Aging Report Example

```json
{
  "customerId": "550e8400-e29b-41d4-a716-446655440001",
  "reportDate": "2024-04-20T00:00:00Z",
  "agingBuckets": {
    "CURRENT": 450.00,
    "DAYS_1_30": 0.00,
    "DAYS_31_60": 450.00,
    "DAYS_61_90": 0.00,
    "DAYS_91_120": 0.00,
    "OVER_120": 0.00
  },
  "totalOutstanding": 900.00
}
```
