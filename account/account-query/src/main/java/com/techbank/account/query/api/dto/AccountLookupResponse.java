package com.techbank.account.query.api.dto;

import java.util.List;

import com.techbank.account.common.dto.BaseResponse;
import com.techbank.account.query.domain.BankAccount;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data @SuperBuilder
@NoArgsConstructor
public class AccountLookupResponse extends BaseResponse {
    private List<BankAccount> accounts;

    public AccountLookupResponse(final String message) {
        super(message);
    }
}
