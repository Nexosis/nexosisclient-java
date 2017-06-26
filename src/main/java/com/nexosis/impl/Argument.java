package com.nexosis.impl;

import org.apache.commons.lang3.StringUtils;

class Argument {
    public static void IsNotNull(Object value, String name)
    {
        if (value == null)
            throw new IllegalArgumentException("Object " + name + " cannot be null.");
    }

    public static void IsNotNullOrEmpty(String value, String name)
    {
        if (StringUtils.isEmpty(value))
            throw new IllegalArgumentException("Value " + name + " cannot be null or empty.");
    }
}
