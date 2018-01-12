package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum DataType {
    STRING("string"),
    NUMERIC("numeric"),
    LOGICAL("logical"),
    DATE("date"),
    NUMERICMEASURE("numericMeasure"),
    TEXT("text");
    private final String value;
    private final static Map<String, DataType> CONSTANTS = new HashMap<String, DataType>();

    static {
        for (DataType c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private DataType(String value) {
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
    public static DataType fromValue(String value) {
        DataType constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }
}