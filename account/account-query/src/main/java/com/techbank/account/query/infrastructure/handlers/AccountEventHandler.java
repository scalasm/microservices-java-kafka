package com.techbank.account.query.infrastructure.handlers;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositedEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.account.query.domain.BankAccountRepository;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class AccountEventHandler implements EventHandler {
    private final BankAccountRepository bankAccountRepository;

    @Override
    public void on(final AccountOpenedEvent event) {
        final var bankAccount = BankAccount.builder()
            .id(event.getId())
            .creationDate(event.getCreatedDate())
            .accountHolder(event.getAccountHolder())
            .accountType(event.getAccountType())
            .balance(event.getOpeningBalance())
            .build();

        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void on(final AccountClosedEvent event) {
        bankAccountRepository.deleteById(event.getId());
    }

    @Override
    public void on(final FundsDepositedEvent event) {
        updateBankAccount(event.getId(), bankAccount -> {
            final var newBalance = bankAccount.getBalance().add(event.getAmount());
            bankAccount.setBalance(newBalance);
        });
    }

    @Override
    public void on(final FundsWithdrawnEvent event) {
        updateBankAccount(event.getId(), bankAccount -> {
            final var newBalance = bankAccount.getBalance().subtract(event.getAmount());
            bankAccount.setBalance(newBalance);
        });
    }

    private void updateBankAccount(final String accountId, final Consumer<? super BankAccount> updateLogic) {
        bankAccountRepository.findById(accountId)
            .ifPresent( bankAccount -> {
                updateLogic.accept(bankAccount);

                bankAccountRepository.save(bankAccount);
            });
    }
}
