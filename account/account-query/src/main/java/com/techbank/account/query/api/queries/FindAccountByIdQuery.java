package com.techbank.account.query.api.queries;

import com.techbank.cqrs.core.queries.BaseQuery;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class FindAccountByIdQuery extends BaseQuery {
    /**
     * Bank account id.
     */
    private String id;
}
