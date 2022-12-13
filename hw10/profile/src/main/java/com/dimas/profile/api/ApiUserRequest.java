package com.dimas.profile.api;

import com.dimas.common.UserStatus;
import com.dimas.validation.CreateGroup;
import com.dimas.validation.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiUserRequest {
    @NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
    private String firstName;
    @NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
    private String lastName;
    @NotBlank(groups = {CreateGroup.class})
    private String login;
    @NotBlank(groups = {CreateGroup.class})
    private String password;
    @Null
    private UUID keycloakId;
    @Null
    private UserStatus status;


}
