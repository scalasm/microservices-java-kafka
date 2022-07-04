package com.techbank.account.cmd;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.techbank.account.cmd.api.commands.CloseAccountCommand;
import com.techbank.account.cmd.api.commands.CommandHandler;
import com.techbank.account.cmd.api.commands.CreateAccountCommand;
import com.techbank.account.cmd.api.commands.DepositFundsCommand;
import com.techbank.account.cmd.api.commands.WithdrawFundsCommand;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;

import lombok.RequiredArgsConstructor;

@SpringBootApplication @RequiredArgsConstructor
public class AccountCommandApplication {

	private final CommandDispatcher commandDispatcher;

	private final CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(AccountCommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		commandDispatcher.registerHandler(CreateAccountCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
	}
}
