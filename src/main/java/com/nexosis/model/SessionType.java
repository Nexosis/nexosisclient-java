package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum SessionType {
    IMPORT("import"),
    FORECAST("forecast"),
    IMPACT("impact"),
    MODEL("model");
    private final String value;
    private final static Map<String, SessionType> CONSTANTS = new HashMap<String, SessionType>();

    static {
        for (SessionType c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    SessionType(String value) {
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
    public static SessionType fromValue(String value) {
        SessionType constant = CONSTANTS.get(value.toLowerCase(Locale.US));
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}