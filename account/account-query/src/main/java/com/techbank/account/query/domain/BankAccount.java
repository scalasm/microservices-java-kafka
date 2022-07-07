package com.techbank.account.query.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BankAccount extends BaseEntity {
    @Id
    private String id;
    private String accountHolder;

    private Date creationDate;

    private AccountType accountType;
    private BigDecimal balance;
}
