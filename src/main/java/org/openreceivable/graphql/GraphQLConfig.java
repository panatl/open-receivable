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

import java.io.File;
import java.io.IOException;

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
     */
    @Bean
    public GraphQLSchema graphQLSchema() throws IOException {
        // Load the schema file
        File schemaFile = new ClassPathResource("graphql/schema.graphqls").getFile();
        
        // Parse the schema
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeRegistry = schemaParser.parse(schemaFile);
        
        // Build runtime wiring with custom scalars
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .scalar(DateTimeScalar.INSTANCE)
                .scalar(BigDecimalScalar.INSTANCE)
                .build();
        
        // Generate the schema with Federation support
        return Federation.transform(typeRegistry, runtimeWiring)
                .fetchEntities(env -> {
                    // Federation entity resolver - to be implemented based on requirements
                    return null;
                })
                .resolveEntityType(env -> {
                    // Entity type resolver - to be implemented based on requirements
                    return null;
                })
                .build();
    }
}
