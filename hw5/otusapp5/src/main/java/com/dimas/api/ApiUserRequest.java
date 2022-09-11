package com.dimas.api;

import com.dimas.util.CreateGroup;
import com.dimas.util.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
}
