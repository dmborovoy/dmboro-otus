package com.dimas.cqrs;

import lombok.*;

import java.util.List;


@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllAccountsQueryResult {
    List<GetAccountQueryResult> results;
}
