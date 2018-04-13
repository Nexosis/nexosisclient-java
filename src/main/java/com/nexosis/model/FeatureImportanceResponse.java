package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class FeatureImportanceResponse extends SessionResponse{
    @JsonProperty("featureImportance")
    private Map<String, Double> scores;

    @JsonProperty("featureImportance")
    public Map<String, Double> getScores(){
        return this.scores;
    }

    @JsonProperty("featureImportance")
    public void setData(String column, double value) {
        this.scores.put(column, value);
    }
}
