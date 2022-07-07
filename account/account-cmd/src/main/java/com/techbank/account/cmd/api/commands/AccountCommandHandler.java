package com.techbank.account.cmd.api.commands;

import org.springframework.stereotype.Service;

import com.techbank.account.cmd.domain.AccountAggregate;
import com.techbank.cqrs.core.handlers.EventSourcingHandler;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class AccountCommandHandler implements CommandHandler {

    private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public void handle(final OpenAccountCommand command) {
        final var aggregate = new AccountAggregate(command);

        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(final DepositFundsCommand command) {
        final var aggregate = eventSourcingHandler.getById(command.getId());

        aggregate.depositFunds(command.getAmount());

        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(final WithdrawFundsCommand command) {
        final var aggregate = eventSourcingHandler.getById(command.getId());

        aggregate.withdrawFunds(command.getAmount());
        
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(final CloseAccountCommand command) {
        final var aggregate = eventSourcingHandler.getById(command.getId());

        aggregate.closeAccount();

        eventSourcingHandler.save(aggregate);
    }
}
