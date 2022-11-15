package com.dimas.profile.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiPermissionRequest {
//    @NotBlank
    private String permissionName;//validation from enum
    private Long permissionId;//validation from enum
    //TODO add batch add
}
