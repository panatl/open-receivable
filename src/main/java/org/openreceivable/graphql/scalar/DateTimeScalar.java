package org.openreceivable.graphql.scalar;

import graphql.language.StringValue;
import graphql.schema.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Custom scalar for DateTime type in GraphQL
 */
public class DateTimeScalar {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("DateTime")
            .description("DateTime scalar type")
            .coercing(new Coercing<LocalDateTime, String>() {
                @Override
                public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                    if (dataFetcherResult instanceof LocalDateTime) {
                        return ((LocalDateTime) dataFetcherResult).format(FORMATTER);
                    }
                    throw new CoercingSerializeException("Expected LocalDateTime");
                }

                @Override
                public LocalDateTime parseValue(Object input) throws CoercingParseValueException {
                    if (input instanceof String) {
                        try {
                            return LocalDateTime.parse((String) input, FORMATTER);
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseValueException("Invalid DateTime format", e);
                        }
                    }
                    throw new CoercingParseValueException("Expected String");
                }

                @Override
                public LocalDateTime parseLiteral(Object input) throws CoercingParseLiteralException {
                    if (input instanceof StringValue) {
                        try {
                            return LocalDateTime.parse(((StringValue) input).getValue(), FORMATTER);
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseLiteralException("Invalid DateTime format", e);
                        }
                    }
                    throw new CoercingParseLiteralException("Expected StringValue");
                }
            })
            .build();
}
