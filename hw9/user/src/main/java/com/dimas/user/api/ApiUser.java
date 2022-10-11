package com.dimas.user.api;


import com.dimas.user.data.model.UserStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@With
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiUser {
    private Long id;
    private UUID accountId;
    private String firstName;
    private String lastName;
    private BigDecimal balance;
    private UserStatus status;
    private LocalDateTime createdOn;
    private LocalDateTime modifiedOn;
}
