package com.dimas.filter;

import lombok.Data;

@Data
public class HeaderStore {
    private String requestId;
    private Long userId;;

    public void clear() {
        requestId = null;
        userId = null;
    }
}
