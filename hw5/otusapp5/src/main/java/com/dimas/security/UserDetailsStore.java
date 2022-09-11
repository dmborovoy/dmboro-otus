package com.dimas.security;

import lombok.Data;

@Data
public class UserDetailsStore {
    private Long userId;
    private String username;

    public void clear() {
        userId = null;
        username = null;
    }
}
