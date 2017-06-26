package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum DataRole {
    NONE("none"),
    TIMESTAMP("timestamp"),
    TARGET("target"),
    FEATURE("feature");
    private final String value;
    private final static Map<String, DataRole> CONSTANTS = new HashMap<>();

    static {
        for (DataRole c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private DataRole(String value) {
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
    public static DataRole fromValue(String value) {
        DataRole constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }
}