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

import com.techbank.account.cmd.api.commands.WithdrawFundsCommand;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/withdraw-funds")
public class WithdrawFundsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawFundsController.class);

    private final CommandDispatcher commandDispatcher;

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable("id") final String id, @RequestBody final WithdrawFundsCommand command) {
        command.setId(id);
        
        commandDispatcher.send(command);

        // TODO Add proper exception handling
        return new ResponseEntity<>(new BaseResponse("Funds withdrawn successfully!"), HttpStatus.OK);
    }
}
