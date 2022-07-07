package com.techbank.account.query.api.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techbank.account.query.api.dto.AccountLookupResponse;
import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.api.queries.FindAccountByHolderQuery;
import com.techbank.account.query.api.queries.FindAccountByIdQuery;
import com.techbank.account.query.api.queries.FindAccountsWithBalanceQuery;
import com.techbank.account.query.api.queries.FindAllAccountsQuery;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import com.techbank.cqrs.core.queries.BaseQuery;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bank-account-lookup")
public class AccountLookupController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountLookupController.class);

    private final QueryDispatcher queryDispatcher;

    @GetMapping("/")
    public ResponseEntity<AccountLookupResponse> findAllAccounts() {
        return executeQuery(new FindAllAccountsQuery());
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<AccountLookupResponse> findAccountById(@PathVariable("id") final String id) {
        return executeQuery(new FindAccountByIdQuery(id));
    }

    @GetMapping("/by-holder/{holderName}")
    public ResponseEntity<AccountLookupResponse> findAccountByHolder(@PathVariable("holderName") final String holderName) {
        return executeQuery(new FindAccountByHolderQuery(holderName));
    }


    @GetMapping("/by-balance")
    public ResponseEntity<AccountLookupResponse> findAccountByBalance(@RequestParam("balance") final BigDecimal balance, @RequestParam("equalityType") final EqualityType equalityType) {
        return executeQuery(new FindAccountsWithBalanceQuery(
            equalityType, balance
        ));
    }

    private ResponseEntity<AccountLookupResponse> executeQuery(final BaseQuery query) {
        final List<BankAccount> bankAccounts = queryDispatcher.send(query);

        if (bankAccounts.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        final var response = AccountLookupResponse.builder()
            .accounts(bankAccounts)
            .message(String.format("Successfully found %d accounts", bankAccounts.size()))
            .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
