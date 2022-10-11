package com.dimas.cqrs;

import lombok.*;

import java.util.List;
import java.util.UUID;


@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllAccountsQuery {
    Long userId;
}
