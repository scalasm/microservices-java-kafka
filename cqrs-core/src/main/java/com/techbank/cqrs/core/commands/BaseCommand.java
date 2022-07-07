package com.techbank.cqrs.core.commands;

import com.techbank.cqrs.core.messages.Message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public abstract class BaseCommand extends Message {
    protected BaseCommand(final String id) {
        super(id);
    } 
}
