package com.nexosis.util;

import com.google.api.client.http.HttpMethods;

import java.util.HashMap;
import java.util.Map;

public enum HttpMethod {
    GET(HttpMethods.GET),
    POST(HttpMethods.POST),
    PUT(HttpMethods.PUT),
    DELETE(HttpMethods.DELETE),
    HEAD(HttpMethods.HEAD),
    PATCH(HttpMethods.PATCH);

    private final String value;
    private final static Map<String, HttpMethod> CONSTANTS = new HashMap<String, HttpMethod>();

    static {
        for (HttpMethod c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    HttpMethod(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String value() {
        return this.value;
    }
}
