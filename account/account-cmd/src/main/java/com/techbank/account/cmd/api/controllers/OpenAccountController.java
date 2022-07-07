package com.techbank.account.cmd.api.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.techbank.account.cmd.api.commands.dto.OpenAccountResponse;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/open-bank-account")
public class OpenAccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAccountController.class);

    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openBankAccount(@RequestBody final OpenAccountCommand command) {
        final var accountId = UUID.randomUUID().toString();
        command.setId(accountId);

        commandDispatcher.send(command);

        // TODO Add proper exception handling
        return new ResponseEntity<>(new OpenAccountResponse("Bank account opened successfully!", accountId), HttpStatus.CREATED);
    }
}
