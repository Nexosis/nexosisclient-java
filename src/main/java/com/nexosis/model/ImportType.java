package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum ImportType {
    S3("s3"),
    Azure("azure"),
    Url("url");

    private final String value;
    private final static Map<String, ImportType> CONSTANTS = new HashMap<String, ImportType>();

    static {
        for (ImportType c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private ImportType(String value) {
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
    public static ImportType fromValue(String value) {
        ImportType constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }
}