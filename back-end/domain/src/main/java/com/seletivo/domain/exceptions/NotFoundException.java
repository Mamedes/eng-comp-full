package com.seletivo.domain.exceptions;

import com.seletivo.domain.AggregateRoot;
import com.seletivo.domain.Identifier;
import com.seletivo.domain.validation.Error;

import java.util.List;

public class NotFoundException extends DomainException {

    protected NotFoundException(final String aMessage, final List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> aggregate,
            final Identifier id
    ) {
        final var error = "%s with ID %s was not found".formatted(
                aggregate.getSimpleName(),
                id.getValue()
        );
        return new NotFoundException(error, List.of(new Error(error)));
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> aggregate,
            final Object id
    ) {
        final var error = "%s with ID %s was not found".formatted(
                aggregate.getSimpleName(),
                id.toString()
        );
        return new NotFoundException(error, List.of(new Error(error)));
    }
}