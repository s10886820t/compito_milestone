package it.skillswap.service;

import java.util.HashMap;
import java.util.Map;

public class IdGenerator {
    private final Map<String, Integer> counters = new HashMap<>();

    public String next(String prefix) {
        int value = counters.getOrDefault(prefix, 0) + 1;
        counters.put(prefix, value);
        return prefix + value;
    }
}