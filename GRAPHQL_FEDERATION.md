# GraphQL Federation v2.9 Setup

This document describes the GraphQL Federation v2.9 implementation for the Open Receivable System.

## Overview

The Open Receivable System now supports GraphQL Federation v2.9, providing a unified API for querying and mutating receivable data. The implementation includes:

- **Federation v2.9 directives** for entity federation
- **Custom scalar types** for DateTime and BigDecimal
- **Query resolvers** for retrieving data
- **Mutation resolvers** for updating data
- **Type resolvers** for computed fields

## Schema Location

The GraphQL schema is located at:
```
src/main/resources/graphql/schema.graphqls
```

## Federation Features

The schema uses the following Federation v2.9 directives:

- `@key`: Defines entity keys for Customer, Receivable, and Payment types
- `@shareable`: Allows types to be shared across subgraphs
- `@external`: Marks fields from other subgraphs
- `@requires`: Specifies required fields for computed fields
- `@provides`: Indicates provided fields

### Entity Types

The following entities support federation:

1. **Customer** - Key field: `customerId`
2. **Receivable** - Key field: `receivableId`
3. **Payment** - Key field: `paymentId`

## Custom Scalars

### DateTime
- Format: ISO-8601 LocalDateTime (e.g., `2023-11-03T10:30:00`)
- Java type: `java.time.LocalDateTime`

### BigDecimal
- Format: String representation of decimal number
- Java type: `java.math.BigDecimal`
- Used for monetary amounts

## Query Operations

### Customer Queries
- `customer(customerId: ID!)` - Get customer by ID
- `customers(status: CustomerStatus)` - List customers, optionally filtered by status

### Receivable Queries
- `receivable(receivableId: ID!)` - Get receivable by ID
- `receivablesByCustomer(customerId: ID!)` - List receivables for a customer
- `receivablesByContract(contractId: ID!)` - List receivables for a contract
- `overdueReceivables` - List all overdue receivables

### Payment Queries
- `payment(paymentId: ID!)` - Get payment by ID
- `paymentsByCustomer(customerId: ID!)` - List payments for a customer

### Aging Queries
- `customerAgingReport(customerId: ID!)` - Get aging report for a customer
- `customerOutstandingBalance(customerId: ID!)` - Get total outstanding balance

## Mutation Operations

### Customer Mutations
- `createCustomer(input: CreateCustomerInput!)` - Create a new customer
- `updateCustomer(input: UpdateCustomerInput!)` - Update an existing customer

### Receivable Mutations
- `createReceivable(input: CreateReceivableInput!)` - Create a new receivable
- `updateReceivable(input: UpdateReceivableInput!)` - Update an existing receivable

### Payment Mutations
- `createPayment(input: CreatePaymentInput!)` - Create a new payment
- `updatePayment(input: UpdatePaymentInput!)` - Update an existing payment

### Aging Mutations
- `updateAging` - Update aging information for all receivables

## Resolver Classes

### Query Resolvers
- `QueryResolver` - Main query resolver
- `CustomerResolver` - Type resolver for Customer computed fields
- `ReceivableResolver` - Type resolver for Receivable computed fields

### Mutation Resolvers
- `MutationResolver` - Main mutation resolver

## Configuration

The GraphQL configuration is located in:
```
src/main/java/org/openreceivable/graphql/GraphQLConfig.java
```

This configuration:
- Registers custom scalars (DateTime, BigDecimal)
- Configures Federation support
- Sets up entity resolvers

## Example Queries

### Query a Customer
```graphql
query {
  customer(customerId: "customer-123") {
    customerId
    fullName
    email
    status
    creditScore
  }
}
```

### Query Receivables for a Customer
```graphql
query {
  receivablesByCustomer(customerId: "customer-123") {
    receivableId
    receivableType
    dueDate
    originalAmount
    outstandingAmount
    isOverdue
    status
  }
}
```

### Get Customer Aging Report
```graphql
query {
  customerAgingReport(customerId: "customer-123") {
    customerId
    current
    days1To30
    days31To60
    days61To90
    days91To120
    over120
    totalOutstanding
  }
}
```

## Example Mutations

### Create a Customer
```graphql
mutation {
  createCustomer(input: {
    customerType: INDIVIDUAL
    firstName: "John"
    lastName: "Doe"
    email: "john.doe@example.com"
    phone: "555-1234"
    address: {
      street1: "123 Main St"
      city: "Springfield"
      state: "IL"
      postalCode: "62701"
      country: "USA"
    }
    status: ACTIVE
    creditScore: 750
  }) {
    customerId
    fullName
    email
  }
}
```

### Update a Receivable
```graphql
mutation {
  updateReceivable(input: {
    receivableId: "receivable-456"
    status: PAID
    paidAmount: "500.00"
    paidDate: "2023-11-03T10:30:00"
  }) {
    receivableId
    status
    paidAmount
    outstandingAmount
  }
}
```

### Create a Payment
```graphql
mutation {
  createPayment(input: {
    customerId: "customer-123"
    paymentDate: "2023-11-03T10:30:00"
    amount: "500.00"
    paymentMethod: ACH
    referenceNumber: "ACH-12345"
    notes: "Monthly payment"
  }) {
    paymentId
    amount
    paymentMethod
    status
  }
}
```

## Integration with Apollo Federation Gateway

To integrate this subgraph with an Apollo Federation Gateway:

1. Start the GraphQL service
2. Configure the gateway to include this subgraph:

```javascript
const gateway = new ApolloGateway({
  serviceList: [
    { name: 'receivables', url: 'http://localhost:8080/graphql' },
    // other subgraphs...
  ],
});
```

## Dependencies

The following dependencies are required for GraphQL Federation support:

- `graphql-java` (21.5)
- `graphql-java-extended-scalars` (21.0)
- `spring-boot-starter-graphql` (3.2.0)
- `spring-boot-starter-web` (3.2.0)
- `federation-graphql-java-support` (4.2.0)

These dependencies are configured in `pom.xml`.

## Notes

- All monetary amounts use BigDecimal for precision
- DateTime uses ISO-8601 format without timezone
- Entity keys are used for federation across subgraphs
- The schema supports both query and mutation operations
- Computed fields (fullName, isOverdue) are resolved via type resolvers
