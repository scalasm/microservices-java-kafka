package com.techbank.account.cmd.infrastructure;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.techbank.account.cmd.domain.AccountAggregate;
import com.techbank.account.cmd.domain.EventStoreRepository;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.events.EventModel;
import com.techbank.cqrs.core.exceptions.AggregateNotFoundException;
import com.techbank.cqrs.core.exceptions.ConcurrencyException;
import com.techbank.cqrs.core.infrastructure.EventStore;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class AccountEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        final var eventStream = eventStoreRepository.findByAggregateId(aggregateId);

        // Optimistick locking check
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        
        var version = expectedVersion;
        for (var event: events) {
            version++;

            event.setVersion(version);
            final var eventModel = EventModel.builder()
                .timestamp(new Date())
                .aggregateId(aggregateId)
                .aggregaeType(AccountAggregate.class.getTypeName())
                .version(version)
                .eventType(event.getClass().getTypeName())
                .eventData(event)
                .build();
            
            final var persistedEvent = eventStoreRepository.save(eventModel);
            if (persistedEvent != null) {
                // TODO Fire event to kafka
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        final var eventStream = eventStoreRepository.findByAggregateId(aggregateId);

        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect account ID provided.");
        }

        return eventStream.stream().map(event -> event.getEventData() ).toList();
    }
    
}
