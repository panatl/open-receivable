# Open Receivable System

A comprehensive receivable management system designed for auto loan and lease organizations. This system provides a complete data model and implementation for tracking customers, vehicles, contracts, receivables, payments, and aging analysis.

## Overview

The Open Receivable System helps auto loan and lease organizations manage their accounts receivable by providing:

- **Customer Management**: Track individual and business customers with full contact and credit information
- **Vehicle Management**: Maintain inventory of vehicles with complete details
- **Contract Management**: Handle both lease and loan agreements with flexible terms
- **Receivable Tracking**: Generate and track all amounts owed, including monthly payments, fees, and charges
- **Payment Processing**: Record and allocate payments to receivables with proper accounting
- **Aging Analysis**: Categorize and report on receivables by age for collection management

## Key Features

### 1. Comprehensive Data Model
- Well-defined entities with clear relationships
- Support for both individual and business customers
- Flexible contract types (lease and loan)
- Multiple receivable types (monthly payments, fees, charges)
- Payment allocation across multiple receivables

### 2. Business Logic
- Automatic generation of receivables from contracts
- Payment processing with allocation to fees, interest, and principal
- Aging calculation and categorization
- Late fee generation for overdue receivables
- Contract termination with appropriate charges

### 3. GraphQL Federation v2.9 API
- Complete GraphQL schema with Federation v2.9 support
- Query and mutation resolvers for all entities
- Custom scalar types (DateTime, BigDecimal)
- Entity federation with @key directives
- Computed fields and type resolvers
- See [GRAPHQL_FEDERATION.md](GRAPHQL_FEDERATION.md) for details

### 4. JSON Schemas
- Complete JSON schema definitions for all entities
- Validation support for data integrity
- Easy integration with REST APIs and NoSQL databases

## Architecture

### Core Components

1. **Model Layer** (`src/main/java/org/openreceivable/model/`)
   - Entity classes: Customer, Vehicle, Contract, Receivable, Payment, etc.
   - Value objects: Address
   - Complete domain model implementation

2. **Repository Layer** (`src/main/java/org/openreceivable/repository/`)
   - Repository interfaces for data access
   - CRUD operations
   - Custom query methods

3. **Service Layer** (`src/main/java/org/openreceivable/service/`)
   - ContractService: Contract lifecycle and receivable generation
   - PaymentService: Payment processing and allocation
   - ReceivableService: Receivable management and aging analysis

4. **Enumerations** (`src/main/java/org/openreceivable/enums/`)
   - CustomerType, CustomerStatus
   - VehicleStatus, VehicleCondition
   - ContractType, ContractStatus
   - ReceivableType, ReceivableStatus
   - PaymentMethod, PaymentStatus
   - AgingCategory

### Data Model

The system uses the following core entities:

- **Customer**: Individual or business customer information
- **Vehicle**: Vehicle details and status
- **Contract**: Lease or loan agreement
- **Receivable**: Amounts owed by customers
- **Payment**: Payments made by customers
- **PaymentAllocation**: Allocation of payments to receivables
- **AgingBucket**: Categorization of receivables by age

See [DATA_MODEL.md](DATA_MODEL.md) for complete data model documentation.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

### Building the Project

```bash
mvn clean compile
```

### Project Structure

```
open-receivable/
├── schemas/                    # JSON schema definitions
│   ├── customer.json
│   ├── vehicle.json
│   ├── contract.json
│   ├── receivable.json
│   ├── payment.json
│   ├── payment-allocation.json
│   └── aging-bucket.json
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/openreceivable/
│   │   │       ├── model/      # Domain models
│   │   │       ├── repository/ # Repository interfaces
│   │   │       ├── service/    # Business logic services
│   │   │       └── enums/      # Enumerations
│   │   └── resources/
│   └── test/
│       └── java/
├── DATA_MODEL.md              # Data model documentation
├── EXAMPLES.md                # Usage examples
├── pom.xml                    # Maven configuration
└── README.md                  # This file
```

## Usage Examples

See [EXAMPLES.md](EXAMPLES.md) for detailed usage examples including:
- Creating customers and vehicles
- Setting up lease and loan contracts
- Processing payments
- Generating aging reports
- Managing late fees and contract termination

## Data Model Highlights

### Customer
- Supports both individual and business customers
- Credit score tracking
- Multiple status levels (Active, Suspended, Closed)

### Contract
- Lease and loan support
- Flexible payment frequencies (Monthly, Bi-weekly, Weekly)
- Residual value for leases
- Mileage limits and excess charges

### Receivable
- Multiple types: Monthly payments, down payments, late fees, excess mileage, damage fees
- Automatic aging calculation
- Status tracking (Pending, Paid, Partial, Overdue, Written Off)

### Payment Processing
- Multiple payment methods supported
- Automatic allocation to receivables
- Priority-based allocation (Fees → Interest → Principal)

### Aging Analysis
- Six aging categories (Current, 1-30, 31-60, 61-90, 91-120, Over 120 days)
- Automatic categorization
- Customer-level aging reports

## Business Rules

1. **Payment Application**: Payments are applied in priority order: Fees → Interest → Principal
2. **Aging Calculation**: Based on days past due date
3. **Late Fees**: Automatically generated for overdue payments
4. **Contract Lifecycle**: Tracks status from active through completion or default
5. **Write-off Policy**: Receivables over 120 days may be eligible for write-off

## Implementation Notes

This is a framework/library implementation that provides:
- Complete domain model
- Repository interfaces (implementation depends on your data store)
- Service layer with business logic
- GraphQL Federation v2.9 API with query and mutation resolvers
- JSON schemas for validation

To use this in your application:
1. Implement the repository interfaces for your chosen data store (JPA, MongoDB, etc.)
2. Wire up the services with your repository implementations
3. Use the GraphQL API for queries and mutations (see [GRAPHQL_FEDERATION.md](GRAPHQL_FEDERATION.md))
4. Alternatively, add REST endpoints or other interfaces as needed
5. Integrate with your authentication and authorization system

## Future Enhancements

Potential areas for expansion:
- REST API implementation (in addition to GraphQL)
- Database migrations
- Reporting engine
- Collections workflow
- Integration with accounting systems
- Document management
- Notifications and alerts
- GraphQL subscriptions for real-time updates

## License

This project is available under an open source license.

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.
