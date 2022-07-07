package com.techbank.account.query.infrastructure.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.api.queries.FindAccountByHolderQuery;
import com.techbank.account.query.api.queries.FindAccountByIdQuery;
import com.techbank.account.query.api.queries.FindAccountsWithBalanceQuery;
import com.techbank.account.query.api.queries.FindAllAccountsQuery;
import com.techbank.account.query.api.queries.QueryHandler;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.account.query.domain.BankAccountRepository;
import com.techbank.cqrs.core.domain.BaseEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountQueryHandler implements QueryHandler {

    private final BankAccountRepository bankAccountRepository;

    @Override
    public List<BaseEntity> handle(final FindAllAccountsQuery query) {
        final Iterable<BankAccount> accounts = bankAccountRepository.findAll();
        
        final List<BaseEntity> bankAccounts = new ArrayList<>();
        
        accounts.forEach(bankAccounts::add);

        return bankAccounts;
    }

    @Override
    public List<BaseEntity> handle(final FindAccountByIdQuery query) {
        final List<BaseEntity> bankAccounts = new ArrayList<>();
        
        bankAccountRepository.findById(query.getId())
            .ifPresent(bankAccounts::add);

        return bankAccounts;
    }

    @Override
    public List<BaseEntity> handle(final FindAccountByHolderQuery query) {
        final List<BaseEntity> bankAccounts = new ArrayList<>();
        
        bankAccountRepository.findByAccountHolder(query.getAccountHolder())
            .ifPresent(bankAccounts::add);

        return bankAccounts;
    }

    @Override
    public List<BaseEntity> handle(final FindAccountsWithBalanceQuery query) {
        final List<BaseEntity> bankAccounts = query.getEqualityType() == EqualityType.LESS_THAN
            ? bankAccountRepository.findByBalanceLessThan(query.getAmount())
            : bankAccountRepository.findByBalanceGreaterThan(query.getAmount()); 

        return bankAccounts;
    }
    
}
