package com.dimas.profile.api;


import com.dimas.common.UserStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@With
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiUser {
    private Long id;
    private UUID keycloakId;
    private String firstName;
    private String lastName;
    private BigDecimal balance;
    private UserStatus status;
    private LocalDateTime createdOn;
    private LocalDateTime modifiedOn;
    List<ApiPermission> permissions;
}
