package com.techbank.account.cmd.api.commands.dto;

import com.techbank.account.common.dto.BaseResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper = true)
public class OpenAccountResponse extends BaseResponse {
    private final String accountId;
    
    public OpenAccountResponse(final String message, final String accountId) {
        super(message);
        this.accountId = accountId;
    }
}
