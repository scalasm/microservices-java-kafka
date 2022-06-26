package com.techbank.cqrs.core.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Base class for all events
 */
@Data
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
public abstract class Message {
    private String id;
}
