package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum SessionStatus {

    REQUESTED("requested"),
    STARTED("started"),
    COMPLETED("completed"),
    CANCELLED("cancelled"),
    ESTIMATED("estimated"),
    FAILED("failed");
    private final String value;
    private final static Map<String, SessionStatus> CONSTANTS = new HashMap<String, SessionStatus>();

    static {
        for (SessionStatus c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private SessionStatus(String value) {
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
    public static SessionStatus fromValue(String value) {
        SessionStatus constant = CONSTANTS.get(value.toLowerCase(Locale.US));
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}