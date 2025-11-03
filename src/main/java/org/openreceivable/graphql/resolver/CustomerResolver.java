package org.openreceivable.graphql.resolver;

import org.openreceivable.model.Customer;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

/**
 * Type resolver for Customer entity
 */
@Controller
public class CustomerResolver {
    
    @SchemaMapping(typeName = "Customer", field = "fullName")
    public String fullName(Customer customer) {
        return customer.getFullName();
    }
}
