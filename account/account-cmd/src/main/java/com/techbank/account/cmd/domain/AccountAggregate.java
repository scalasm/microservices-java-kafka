package com.techbank.account.cmd.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.techbank.account.cmd.api.commands.CreateAccountCommand;
import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositedEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;
import com.techbank.cqrs.core.domain.AggregateRoot;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    /**
     * Is the account active or not?
     */
    private boolean isActive;

    /**
     * Current account balance.
     */
    private BigDecimal balance;

    public AccountAggregate(final CreateAccountCommand command) {
        raiseEvent(
            AccountOpenedEvent.builder()
                .id(command.getId())  
                .accountHolder(command.getAccoutHolder())
                .accountType(command.getAccountType())
                .createdDate(new Date())
                .build()
        );
    }

    public void apply(final AccountOpenedEvent event) {
         this.id = event.getId();
         this.balance = event.getOpeningBalance();
         this.isActive = true;
    }

    public void depositFunds(final BigDecimal amount) {
        if (!isActive) {
            throw new IllegalStateException("Funds cannot be deposited in an inactive account!");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cannot deposit a negative amount.");
        }

        raiseEvent(
            FundsDepositedEvent.builder()
                .id(id)
                .amount(amount)
                .build()
        );
    }

    public void apply(final FundsDepositedEvent event) {
        this.balance = this.balance.add(event.getAmount());
    }

    public void withdrawFunds(final BigDecimal amount) {
        if (!isActive) {
            throw new IllegalStateException("Funds cannot be withdrawn from an inactive account!");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cannot withdraw a negative amount.");
        }
        
        raiseEvent(
            FundsWithdrawnEvent.builder()
                .id(id)
                .amount(amount)
                .build()
        );
    }

    public void apply(final FundsWithdrawnEvent event) {
        this.balance = this.balance.subtract(event.getAmount());        
    }

    public void closeAccount() {
        if (!isActive) {
            throw new IllegalStateException("Account is already closed!");
        }

        raiseEvent(
            AccountClosedEvent.builder()
                .id(id)
                .build()
        );
    }

    public void apply(final AccountClosedEvent event) {
        this.isActive = false;
    }
}
