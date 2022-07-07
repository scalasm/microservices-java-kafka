package com.techbank.account.cmd.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techbank.account.cmd.api.commands.DepositFundsCommand;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/deposit-funds")
public class DepositFundsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepositFundsController.class);

    private final CommandDispatcher commandDispatcher;

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable("id") final String id, @RequestBody final DepositFundsCommand command) {
        command.setId(id);
        
        commandDispatcher.send(command);

        // TODO Add proper exception handling
        return new ResponseEntity<>(new BaseResponse("Funds deposited successfully!"), HttpStatus.OK);
    }
}
