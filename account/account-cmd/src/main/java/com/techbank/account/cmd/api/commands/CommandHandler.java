package com.techbank.account.cmd.api.commands;

public interface CommandHandler {
    void handle(CreateAccountCommand command);
    void handle(DepositFundsCommand command);
    void handle(WithdrawFundsCommand command);
    void handle(CloseAccountCommand command);
}
