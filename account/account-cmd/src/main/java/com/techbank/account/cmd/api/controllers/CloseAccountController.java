package com.techbank.account.cmd.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techbank.account.cmd.api.commands.CloseAccountCommand;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/close-bank-account")
public class CloseAccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CloseAccountController.class);

    private final CommandDispatcher commandDispatcher;

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable("id") final String id) {
        commandDispatcher.send(
            new CloseAccountCommand(id)
        );

        // TODO Add proper exception handling
        return new ResponseEntity<>(new BaseResponse("Bank account closed successfully!"), HttpStatus.OK);
    }
}
