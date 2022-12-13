package com.dimas.gw.utils;

import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    public static String mapToString(Map<String, String> map) {
        return map.keySet().stream()
                .map(key -> key + ":" + map.get(key))
                .collect(Collectors.joining(", "));
    }
}
