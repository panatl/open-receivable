package org.openreceivable.graphql.resolver;

import org.openreceivable.model.Customer;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

/**
 * Reactive type resolver for Customer entity
 */
@Controller
public class CustomerResolver {
    
    @SchemaMapping(typeName = "Customer", field = "fullName")
    public Mono<String> fullName(Customer customer) {
        return Mono.fromCallable(() -> customer.getFullName());
    }
}
