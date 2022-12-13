package com.dimas.cqrs;

import lombok.*;


@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllAccountsQuery {
    Long userId;
}
