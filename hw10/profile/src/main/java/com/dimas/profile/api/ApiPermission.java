package com.dimas.profile.api;


import com.dimas.common.UserStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@With
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiPermission {
    private Long id;
    private String name;
}
