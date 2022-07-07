package com.techbank.cqrs.core.handlers;

import com.techbank.cqrs.core.domain.AggregateRoot;

/**
 * 
 */
public interface EventSourcingHandler<T> {
    /**
     * Save the current aggregate and its pending change events.
     * @param aggregate
     */
    void save(AggregateRoot aggregate);

    /**
     * Returns the latest state of the aggregate.
     */
    T getById(String aggregateId);

    /**
     * Republish all events for all aggregates according to their timeline.
     * This is particularly useful when there is need for recreating the read-only views.
     */
    void republishEvents();
}
