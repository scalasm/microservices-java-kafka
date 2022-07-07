package com.techbank.cqrs.core.producers;

import com.techbank.cqrs.core.events.BaseEvent;

public interface EventProducer {
    /**
     * Publish a new event on the specified topic
     * @param topic the topic to publish the event to
     * @param event the event
     */
    void produce(String topic, BaseEvent event);
}
