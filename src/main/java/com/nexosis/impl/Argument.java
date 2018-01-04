package com.nexosis.impl;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

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

    public static void OneOfIsNotNullOrEmpty(Map.Entry<Object, String> ...args) {
        int threshold = args.length;
        int value = 0;
        String listOfKeys = "";

        for (Map.Entry<Object,String> arg : args) {
            if (arg.getKey() == null) {
                value++;
            }

            listOfKeys += arg.getValue() + ", ";
        }

        if (value >= threshold) {
            listOfKeys=listOfKeys.substring(0,listOfKeys.length()-2);
            throw new IllegalArgumentException("One of " + listOfKeys + " should not be null or empty.");
        }
    }
}
