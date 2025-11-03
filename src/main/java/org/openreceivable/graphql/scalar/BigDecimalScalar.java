package org.openreceivable.graphql.scalar;

import graphql.language.StringValue;
import graphql.schema.*;

import java.math.BigDecimal;

/**
 * Custom scalar for BigDecimal type in GraphQL
 */
public class BigDecimalScalar {
    
    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("BigDecimal")
            .description("BigDecimal scalar type for monetary values")
            .coercing(new Coercing<BigDecimal, String>() {
                @Override
                public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                    if (dataFetcherResult instanceof BigDecimal) {
                        return ((BigDecimal) dataFetcherResult).toPlainString();
                    }
                    throw new CoercingSerializeException("Expected BigDecimal");
                }

                @Override
                public BigDecimal parseValue(Object input) throws CoercingParseValueException {
                    if (input instanceof String) {
                        try {
                            return new BigDecimal((String) input);
                        } catch (NumberFormatException e) {
                            throw new CoercingParseValueException("Invalid BigDecimal format", e);
                        }
                    } else if (input instanceof Integer) {
                        return BigDecimal.valueOf((Integer) input);
                    } else if (input instanceof Long) {
                        return BigDecimal.valueOf((Long) input);
                    } else if (input instanceof Double) {
                        return BigDecimal.valueOf((Double) input);
                    } else if (input instanceof Float) {
                        return BigDecimal.valueOf((Float) input);
                    }
                    throw new CoercingParseValueException("Expected String or Number");
                }

                @Override
                public BigDecimal parseLiteral(Object input) throws CoercingParseLiteralException {
                    if (input instanceof StringValue) {
                        try {
                            return new BigDecimal(((StringValue) input).getValue());
                        } catch (NumberFormatException e) {
                            throw new CoercingParseLiteralException("Invalid BigDecimal format", e);
                        }
                    }
                    throw new CoercingParseLiteralException("Expected StringValue");
                }
            })
            .build();
}
