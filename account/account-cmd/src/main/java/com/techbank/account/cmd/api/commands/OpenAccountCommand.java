package com.techbank.account.cmd.api.commands;

import java.math.BigDecimal;

import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.commands.BaseCommand;

import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accoutHolder;
    private AccountType accountType;
    private BigDecimal openingBalance;
}
