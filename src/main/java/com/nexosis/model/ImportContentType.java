package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum ImportContentType {
    Csv("text/csv"),
    Json("application/JSON");

    private final String value;
    private final static Map<String, ImportContentType> CONSTANTS = new HashMap<>();

    static {
        for (ImportContentType c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    ImportContentType(String value) {
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
    public static ImportContentType fromValue(String value) {
        ImportContentType constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }
}