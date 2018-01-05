package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class ViewData extends ViewDefinition {
    @JsonProperty("data")
    private List<Map<String, String>> data = null;

    @JsonProperty("data")
    public List<Map<String, String>> getData() {
        return data;
    }
}
