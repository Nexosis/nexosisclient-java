package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum ImputationStrategy {
    ZEROES("zeroes"),
    MEAN("mean"),
    MEDIAN("median"),
    MODE("mode"),
    MIN("min"),
    MAX("max");
    private final String value;
    private final static Map<String, ImputationStrategy> CONSTANTS = new HashMap<String, ImputationStrategy>();

    static {
        for (ImputationStrategy c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    ImputationStrategy(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static ImputationStrategy fromValue(String value) {
        ImputationStrategy constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }
}
