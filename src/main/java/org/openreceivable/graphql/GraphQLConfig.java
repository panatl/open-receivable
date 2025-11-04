package org.openreceivable.graphql;

import com.apollographql.federation.graphqljava.Federation;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.openreceivable.graphql.scalar.BigDecimalScalar;
import org.openreceivable.graphql.scalar.DateTimeScalar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * GraphQL Configuration with Federation v2.9 support
 */
@Configuration
public class GraphQLConfig {
    
    /**
     * Configure runtime wiring with custom scalars
     */
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(DateTimeScalar.INSTANCE)
                .scalar(BigDecimalScalar.INSTANCE);
    }
    
    /**
     * Configure GraphQL schema with Federation support
     * Note: Entity resolvers are not implemented yet. They will be added when
     * cross-subgraph entity resolution is needed.
     */
    @Bean
    public GraphQLSchema graphQLSchema() throws IOException {
        // Load the schema from the classpath as a stream (works both in IDE and fat JAR)
        ClassPathResource resource = new ClassPathResource("graphql/schema.graphqls");

        // Parse the schema from a Reader to avoid relying on a real filesystem path
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeRegistry;
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            typeRegistry = schemaParser.parse(reader);
        }
        
        // Build runtime wiring with custom scalars
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .scalar(DateTimeScalar.INSTANCE)
                .scalar(BigDecimalScalar.INSTANCE)
                .build();
        
        // Generate the schema with Federation support
        // Entity resolvers will be implemented when cross-subgraph resolution is needed
        return Federation.transform(typeRegistry, runtimeWiring)
                .fetchEntities(env -> {
                    // TODO: Implement entity fetching when needed for cross-subgraph queries
                    // For now, return an empty list as entities are fetched via regular queries
                    return java.util.Collections.emptyList();
                })
                .resolveEntityType(env -> {
                    // TODO: Implement type resolution when needed for cross-subgraph queries
                    // For now, use the __typename from the entity
                    Object entity = env.getObject();
                    if (entity instanceof java.util.Map) {
                        String typename = (String) ((java.util.Map<?, ?>) entity).get("__typename");
                        return env.getSchema().getObjectType(typename);
                    }
                    throw new RuntimeException("Unable to resolve entity type");
                })
                .build();
    }

    /**
     * Provide a GraphQlSource backed by the federated GraphQLSchema so that
     * Spring GraphQL does not try to parse the SDL itself (which would not know
     * about Federation directives).
     */
    @Bean
    public GraphQlSource graphQlSource(GraphQLSchema schema, RuntimeWiringConfigurer runtimeWiringConfigurer) {
        // Runtime wiring (e.g., custom scalars) is already applied when building the schema.
        // Provide the pre-built schema directly to Spring GraphQL.
        return GraphQlSource.builder(schema).build();
    }
}
