# Quick Start Guide

## What is Open Receivable?

Open Receivable is a complete data model and Java implementation for managing receivables in auto loan and lease organizations. It provides everything needed to track customers, vehicles, contracts, payments, and aging analysis.

## What's Included?

### 📊 Data Model
- **8 Core Entities**: Customer, Vehicle, Contract, Receivable, Payment, PaymentAllocation, AgingBucket, Address
- **Complete Relationships**: One-to-many, many-to-many with proper foreign keys
- **Business Rules**: Payment allocation, aging calculation, contract lifecycle

### 🔧 JSON Schemas
8 JSON schema files in the `schemas/` directory that can be used for:
- Data validation
- API contracts
- Database schema generation
- Documentation

### ☕ Java Implementation
- **30 Java classes** (~2,000 lines of code)
- **12 Enumerations** for type safety
- **8 Model classes** with business logic
- **7 Repository interfaces** for data access
- **3 Service classes** with business operations

### 📚 Documentation
- **README.md** - System overview and getting started
- **DATA_MODEL.md** - Complete entity specifications
- **ARCHITECTURE.md** - System architecture and design patterns
- **EXAMPLES.md** - Java code usage examples
- **JSON_EXAMPLES.md** - JSON document examples
- **QUICK_START.md** - This file!

## Project Structure

```
open-receivable/
├── schemas/                     # JSON schemas for all entities
│   ├── customer.json
│   ├── vehicle.json
│   ├── contract.json
│   ├── receivable.json
│   ├── payment.json
│   ├── payment-allocation.json
│   ├── aging-bucket.json
│   └── address.json
│
├── src/main/java/org/openreceivable/
│   ├── enums/                   # Type enumerations
│   │   ├── CustomerType.java
│   │   ├── ContractType.java
│   │   ├── ReceivableType.java
│   │   └── ... (9 more)
│   │
│   ├── model/                   # Domain models
│   │   ├── Customer.java
│   │   ├── Vehicle.java
│   │   ├── Contract.java
│   │   ├── Receivable.java
│   │   ├── Payment.java
│   │   ├── PaymentAllocation.java
│   │   ├── AgingBucket.java
│   │   └── Address.java
│   │
│   ├── repository/              # Data access interfaces
│   │   ├── CustomerRepository.java
│   │   ├── VehicleRepository.java
│   │   ├── ContractRepository.java
│   │   ├── ReceivableRepository.java
│   │   ├── PaymentRepository.java
│   │   ├── PaymentAllocationRepository.java
│   │   └── AgingBucketRepository.java
│   │
│   └── service/                 # Business logic
│       ├── ContractService.java
│       ├── PaymentService.java
│       └── ReceivableService.java
│
├── pom.xml                      # Maven build file
└── [Documentation files]
```

## 5-Minute Quick Start

### 1. Build the Project
```bash
mvn clean compile
```

### 2. Use the Model in Your Code

```java
import org.openreceivable.model.*;
import org.openreceivable.enums.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// Create a customer
Customer customer = new Customer();
customer.setCustomerType(CustomerType.INDIVIDUAL);
customer.setFirstName("John");
customer.setLastName("Doe");
customer.setEmail("john@example.com");
customer.setStatus(CustomerStatus.ACTIVE);

// Create a vehicle
Vehicle vehicle = new Vehicle();
vehicle.setVin("1HGBH41JXMN109186");
vehicle.setMake("Honda");
vehicle.setModel("Accord");
vehicle.setYear(2023);
vehicle.setStatus(VehicleStatus.AVAILABLE);

// Create a lease contract
Contract contract = new Contract();
contract.setCustomerId(customer.getCustomerId());
contract.setVehicleId(vehicle.getVehicleId());
contract.setContractType(ContractType.LEASE);
contract.setTerm(36);
contract.setMonthlyPayment(new BigDecimal("450.00"));
contract.setStatus(ContractStatus.ACTIVE);
```

### 3. Implement Repository Interfaces

Choose your data store and implement the repository interfaces:

```java
// Example: JPA implementation
public class JpaCustomerRepository implements CustomerRepository {
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Customer save(Customer customer) {
        return em.merge(customer);
    }
    
    @Override
    public Optional<Customer> findById(String id) {
        return Optional.ofNullable(em.find(Customer.class, id));
    }
    // ... implement other methods
}
```

### 4. Use Service Layer

```java
// Wire up services
ContractService contractService = new ContractService(
    contractRepository, 
    receivableRepository
);

// Create contract and generate receivables
Contract newContract = contractService.createContract(contract);
List<Receivable> receivables = contractService.generateMonthlyReceivables(
    newContract.getContractId()
);
```

## Common Use Cases

### 🏦 Creating a Lease
1. Create Customer
2. Create Vehicle
3. Create Contract (LEASE type)
4. System generates down payment receivable
5. Generate monthly payment receivables

### 💰 Processing a Payment
1. Customer makes payment
2. Create Payment record
3. System allocates payment to receivables
4. Updates receivable statuses
5. Creates payment allocation records

### 📈 Aging Analysis
1. Run aging update process
2. System calculates days past due
3. Categorizes receivables into buckets
4. Generates aging reports
5. Identifies collections candidates

### ⚠️ Late Fee Management
1. System identifies overdue receivables
2. Generates late fee receivables
3. Adds to customer's outstanding balance
4. Sends notifications (when integrated)

## Key Features

✅ **Flexible Contract Types** - Support for both leases and loans
✅ **Multiple Receivable Types** - Monthly payments, fees, charges
✅ **Payment Allocation** - Split payments across multiple receivables
✅ **Aging Analysis** - 6 aging categories for collection management
✅ **Business Rules** - Built-in validation and calculations
✅ **Type Safety** - Strong typing with enumerations
✅ **Clean Architecture** - Layered design with clear separation
✅ **Database Agnostic** - Use any data store via repository pattern
✅ **Well Documented** - Comprehensive documentation and examples

## Next Steps

### For Developers
1. **Implement Repositories** - Choose your data store (JPA, MongoDB, etc.)
2. **Add REST API** - Expose services via REST endpoints
3. **Add Authentication** - Secure the system
4. **Add UI** - Build web or mobile interface
5. **Add Reporting** - Generate business reports

### For Database Designers
1. **Review DATA_MODEL.md** - Understand entities and relationships
2. **Create Schema** - Use JSON schemas or generate from models
3. **Add Indexes** - Follow indexing recommendations
4. **Set Up Partitioning** - For large-scale deployments

### For Architects
1. **Review ARCHITECTURE.md** - Understand system design
2. **Plan Integrations** - Connect to existing systems
3. **Design Scalability** - Plan for growth
4. **Security Planning** - Implement security layers

### For Business Analysts
1. **Review DATA_MODEL.md** - Understand business entities
2. **Review EXAMPLES.md** - See real-world scenarios
3. **Customize Business Rules** - Adapt to your needs
4. **Define Reports** - Specify required reporting

## Technology Stack

- **Java 11+** - Core language
- **Maven** - Build tool
- **Any Database** - Via repository implementation
- **Any Framework** - Spring Boot, Quarkus, etc.

## Support and Documentation

- **Complete Examples**: See EXAMPLES.md
- **Data Model Details**: See DATA_MODEL.md
- **Architecture Overview**: See ARCHITECTURE.md
- **JSON Format**: See JSON_EXAMPLES.md

## What This System Does NOT Include

This is a **data model and business logic framework**. You'll need to add:
- Database implementation (JPA entities, MongoDB repositories, etc.)
- REST API layer
- User interface
- Authentication/Authorization
- Email/SMS notifications
- Report generation
- Document management

## Compilation Status

✅ Successfully compiles with Maven
✅ 30 Java classes
✅ ~2,000 lines of code
✅ Zero compilation errors
✅ Clean code structure

## Building

```bash
# Compile
mvn clean compile

# Package (when you add tests)
mvn clean package

# Install to local repo
mvn clean install
```

## License

Open source - feel free to use and modify for your needs.

## Contributing

Contributions welcome! The system is designed to be extended:
- Add new receivable types
- Add new payment methods
- Customize business rules
- Add reporting capabilities
- Integrate with external systems

---

**Ready to use! Start by implementing the repository interfaces for your chosen data store.**
