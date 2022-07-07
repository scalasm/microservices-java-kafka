package com.techbank.account.query.infrastructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techbank.cqrs.core.domain.BaseEntity;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import com.techbank.cqrs.core.queries.BaseQuery;
import com.techbank.cqrs.core.queries.QueryHandlerMethod;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        // TODO Auto-generated method stub
        final var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());

        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        final var handlers = routes.get(query.getClass());

        if (handlers == null || handlers.isEmpty()) {
            throw new RuntimeException("No handler found for this query type.");
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("Could not dispatch query to more than one handler!");
        }

        return handlers.get(0).handle(query);
    }
}
