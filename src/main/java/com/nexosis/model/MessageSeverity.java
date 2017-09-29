package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum MessageSeverity {
    INFORMATIONAL("informational"),
    WARNING("warning"),
    ERROR("error"),
    DEBUG("debug");

    private final String value;
    private final static Map<String, MessageSeverity> CONSTANTS = new HashMap<String, MessageSeverity>();

    static {
        for (MessageSeverity c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    MessageSeverity(String value) {
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
    public static MessageSeverity fromValue(String value) {
        MessageSeverity constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }
}
