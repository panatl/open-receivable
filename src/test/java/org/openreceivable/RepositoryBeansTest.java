package org.openreceivable;

import org.junit.jupiter.api.Test;
import org.openreceivable.repository.*;
import org.openreceivable.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test to verify that all required beans are properly created by Spring
 */
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    classes = {
        org.openreceivable.repository.impl.InMemoryCustomerRepository.class,
        org.openreceivable.repository.impl.InMemoryPaymentRepository.class,
        org.openreceivable.repository.impl.InMemoryReceivableRepository.class,
        org.openreceivable.repository.impl.InMemoryAgingBucketRepository.class,
        org.openreceivable.repository.impl.InMemoryContractRepository.class,
        org.openreceivable.repository.impl.InMemoryVehicleRepository.class,
        org.openreceivable.repository.impl.InMemoryPaymentAllocationRepository.class,
        org.openreceivable.service.ReceivableService.class,
        org.openreceivable.service.PaymentService.class,
        org.openreceivable.service.ContractService.class
    }
)
@TestPropertySource(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.graphql.GraphQlAutoConfiguration"
})
public class RepositoryBeansTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testCustomerRepositoryBeanExists() {
        CustomerRepository repository = context.getBean(CustomerRepository.class);
        assertNotNull(repository, "CustomerRepository bean should be created");
    }

    @Test
    public void testPaymentRepositoryBeanExists() {
        PaymentRepository repository = context.getBean(PaymentRepository.class);
        assertNotNull(repository, "PaymentRepository bean should be created");
    }

    @Test
    public void testReceivableRepositoryBeanExists() {
        ReceivableRepository repository = context.getBean(ReceivableRepository.class);
        assertNotNull(repository, "ReceivableRepository bean should be created");
    }

    @Test
    public void testAgingBucketRepositoryBeanExists() {
        AgingBucketRepository repository = context.getBean(AgingBucketRepository.class);
        assertNotNull(repository, "AgingBucketRepository bean should be created");
    }

    @Test
    public void testContractRepositoryBeanExists() {
        ContractRepository repository = context.getBean(ContractRepository.class);
        assertNotNull(repository, "ContractRepository bean should be created");
    }

    @Test
    public void testVehicleRepositoryBeanExists() {
        VehicleRepository repository = context.getBean(VehicleRepository.class);
        assertNotNull(repository, "VehicleRepository bean should be created");
    }

    @Test
    public void testPaymentAllocationRepositoryBeanExists() {
        PaymentAllocationRepository repository = context.getBean(PaymentAllocationRepository.class);
        assertNotNull(repository, "PaymentAllocationRepository bean should be created");
    }

    @Test
    public void testReceivableServiceBeanExists() {
        ReceivableService service = context.getBean(ReceivableService.class);
        assertNotNull(service, "ReceivableService bean should be created");
    }

    @Test
    public void testPaymentServiceBeanExists() {
        PaymentService service = context.getBean(PaymentService.class);
        assertNotNull(service, "PaymentService bean should be created");
    }

    @Test
    public void testContractServiceBeanExists() {
        ContractService service = context.getBean(ContractService.class);
        assertNotNull(service, "ContractService bean should be created");
    }
}
