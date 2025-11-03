package org.openreceivable.graphql.resolver;

import org.openreceivable.model.Receivable;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

/**
 * Reactive type resolver for Receivable entity
 */
@Controller
public class ReceivableResolver {
    
    @SchemaMapping(typeName = "Receivable", field = "isOverdue")
    public Mono<Boolean> isOverdue(Receivable receivable) {
        return Mono.fromCallable(() -> receivable.isOverdue());
    }
}
