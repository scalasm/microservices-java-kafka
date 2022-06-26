package com.techbank.account.cmd.api.commands;

import java.math.BigDecimal;

import com.techbank.cqrs.core.commands.BaseCommand;

import lombok.Data;

@Data
public class WithdrawFundsCommand extends BaseCommand {
    private BigDecimal amount;    
}
