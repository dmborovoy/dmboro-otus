package com.dimas.api;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiRegisterRequest {//remove it
    private Long userId;//remove it
    private String login;
    private String password;
}
