package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum PredictionDomain {
    REGRESSION("regression"),
    CLASSIFICATION("classification");
    private final String value;
    private final static Map<String, PredictionDomain> CONSTANTS = new HashMap<>();

    static {
        for (PredictionDomain c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    PredictionDomain(String value) {
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
    public static PredictionDomain fromValue(String value) {
        PredictionDomain constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }
}
