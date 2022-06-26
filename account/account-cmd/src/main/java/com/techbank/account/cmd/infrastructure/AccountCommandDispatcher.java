package com.techbank.account.cmd.infrastructure;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techbank.cqrs.core.commands.BaseCommand;
import com.techbank.cqrs.core.domain.CommandHandlerMethod;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {

//    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod<? extends BaseCommand>>> routes = Map.of();
    private final Map<Class<? extends BaseCommand>, CommandHandlerMethod> routes = Map.of();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod handler) {
        // final var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        // handlers.add(handler);
        routes.put(type, handler);
    }

    @Override
    public void send(BaseCommand command) {
        // final var handlers = routes.get(command.getClass());

        // if (handlers == null || handlers.isEmpty()) {
        //     throw new RuntimeException(String.format("No command handler found for command %s", command.getClass().getSimpleName()));
        // }
        // // XXX Why should I support this multiple handlers case?
        // if (handlers.size() > 1) {
        //     throw new RuntimeException("Cannot send command to multiple command handlers");
        // }
        // handlers.get(0).handle(command);
        final var handler = routes.get(command.getClass());

        if (handler == null) {
            throw new RuntimeException(String.format("No command handler found for command %s", command.getClass().getSimpleName()));
        }
        handler.handle(command);
    }
}
