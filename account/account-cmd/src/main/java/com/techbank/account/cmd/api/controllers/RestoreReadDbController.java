package com.techbank.account.cmd.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techbank.account.cmd.api.commands.RestoreReadDbCommand;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;

import lombok.RequiredArgsConstructor;

/**
 * Administrative entrypoint for recreating all read-only views.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/restore-read-db")
public class RestoreReadDbController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestoreReadDbController.class);

    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadOnlyDatabases() {
        commandDispatcher.send(new RestoreReadDbCommand());

        // TODO Add proper exception handling
        return new ResponseEntity<>(new BaseResponse("Restore request for read-only database(s) completed successfully!"), HttpStatus.CREATED);
    }
}
