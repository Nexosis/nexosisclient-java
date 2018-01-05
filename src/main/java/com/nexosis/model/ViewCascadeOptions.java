package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

// cascade
public enum ViewCascadeOptions {
    CASCADE_SESSIONS("sessions");

    public static final EnumSet<ViewCascadeOptions> CASCADE_NONE = EnumSet.noneOf(ViewCascadeOptions.class);

    private final String value;
    private final static Map<String, ViewCascadeOptions> CONSTANTS = new HashMap<String, ViewCascadeOptions>();

    static {
        for (ViewCascadeOptions c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private ViewCascadeOptions(String value) {
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
    public static ViewCascadeOptions fromValue(String value) {
        ViewCascadeOptions constant = CONSTANTS.get(value.toLowerCase(Locale.US));
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }
}
