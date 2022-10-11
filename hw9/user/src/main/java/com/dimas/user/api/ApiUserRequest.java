package com.dimas.user.api;

import com.dimas.user.data.model.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiUserRequest {
    private String firstName;
    private String lastName;
    private UUID accountId;
    private UserStatus status;
}
