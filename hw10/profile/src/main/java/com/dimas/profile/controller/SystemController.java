package com.dimas.profile.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.Assert.notNull;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class SystemController {

    @GetMapping("/system")
    public String container() {
        return "System:\n" + mapToString(System.getenv());
    }

    @GetMapping("/hostname")
    public String hostname() {
        return "HOSTNAME=" + System.getenv("HOSTNAME");
    }

    private static String mapToString(Map<String, String> map) {
        notNull(map, "map cannot be null");
        return map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining("\n"));
    }
}
