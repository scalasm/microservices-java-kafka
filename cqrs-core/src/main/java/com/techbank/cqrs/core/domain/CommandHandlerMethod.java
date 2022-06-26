package com.techbank.cqrs.core.domain;

import com.techbank.cqrs.core.commands.BaseCommand;

@FunctionalInterface
public interface CommandHandlerMethod {
    <T extends BaseCommand> void handle(T command);
}
