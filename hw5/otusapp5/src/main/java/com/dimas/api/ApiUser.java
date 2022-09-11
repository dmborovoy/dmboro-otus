package com.dimas.api;


import lombok.*;

import java.util.UUID;

@With
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiUser {
    private Long id;
    private String kcId;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
}
