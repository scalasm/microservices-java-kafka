package com.techbank.cqrs.core.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techbank.cqrs.core.events.BaseEvent;

/**
 * Base class for all aggregate roots in the domain.
 */
public abstract class AggregateRoot {
    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateRoot.class);
    
    protected String id;

    private int version = -1;

    private final List<BaseEvent> changes = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public List<BaseEvent> getUncommittedEvents() {
        return Collections.unmodifiableList(this.changes);
    }

    public void markChangesAsCommitted() {
        changes.clear();
    }

    protected void applyChange(final BaseEvent event, final boolean isNewEvent) {
        try {
            final var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            LOGGER.warn("No apply method found for handling the event {}", event.getClass().getSimpleName());
        } catch (Exception e) {
            LOGGER.error("Error while applying the method to the aggregate!", e);
        } finally {
            if (isNewEvent) {
                this.changes.add(event);
            }
        }
    }

    public void raiseEvent(final BaseEvent event) {
        applyChange(event, true);
    }

    public void replayeEvents(final Iterable<BaseEvent> events) {
        events.forEach(event -> applyChange(event, false));
    }
}
