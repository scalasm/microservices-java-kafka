package com.techbank.cqrs.core.commands;

/**
 * Dispatches the events to the appropriate handler method.
 */
public interface CommandDispatcher {
    /**
     * Register a new command handler.
     * 
     * @param type the command type
     * @param handler the handler for the command type
     * 
     */
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod handler);

    /**
     * Dispatches a command to the appropriate event handler.
     * @param command the command to dispatch
     */
    void send(BaseCommand command);
}
