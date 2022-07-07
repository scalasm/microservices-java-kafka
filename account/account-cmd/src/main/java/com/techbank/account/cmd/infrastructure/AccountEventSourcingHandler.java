package com.techbank.account.cmd.infrastructure;

import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.techbank.account.cmd.domain.AccountAggregate;
import com.techbank.cqrs.core.domain.AggregateRoot;
import com.techbank.cqrs.core.handlers.EventSourcingHandler;
import com.techbank.cqrs.core.infrastructure.EventStore;
import com.techbank.cqrs.core.producers.EventProducer;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;

    private final EventProducer eventProducer;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedEvents(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String aggregateId) {
        final AccountAggregate aggregate = new AccountAggregate();

        final var events = eventStore.getEvents(aggregateId);
        if (events != null && !events.isEmpty()) {
            aggregate.replayeEvents(events);
            var latestVersion = events.stream().map(event -> event.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for (var aggregateId: aggregateIds) {
            var aggregate = getById(aggregateId);
            // Just skip accounts that are inactive anyway - we spare some traffic and useless processing anyway
            if (aggregate != null && aggregate.isActive()) {
                var eventStream = eventStore.getEvents(aggregateId);
                
                eventStream.forEach(event -> {
                    final var topic = event.getClass().getSimpleName();
                    eventProducer.produce(topic, event);
                } );
            }
        }
    }
}
