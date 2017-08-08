package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum AggregationStrategy {
    SUM("sum"),
    MEAN("mean"),
    MEDIAN("median"),
    MODE("mode");
    private final String value;
    private final static Map<String, AggregationStrategy> CONSTANTS = new HashMap<String, AggregationStrategy>();

    static {
        for (AggregationStrategy c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private AggregationStrategy(String value) {
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
    public static AggregationStrategy fromValue(String value) {
        AggregationStrategy constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }
}
