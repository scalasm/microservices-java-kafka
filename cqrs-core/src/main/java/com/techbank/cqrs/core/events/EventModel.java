package com.techbank.cqrs.core.events;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

/**
 * Schema for the aggregate events stored in the Event Store.
 */
@Data
@Builder
@Document(collection = "eventStore")
public class EventModel {
    /**
     * Event UID.
     */
    @Id
    private String id;

    /**
     * Creation timestamp.
     */
    private Date timestamp;

    private String aggregateId;
    private String aggregaeType;
    private int version;

    private String eventType;
    private BaseEvent eventData;
}
