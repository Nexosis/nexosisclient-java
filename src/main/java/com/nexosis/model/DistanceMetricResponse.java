package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DistanceMetricResponse extends SessionResponse {
    @JsonProperty("data")
    private DistanceMetric[] data;

    @JsonProperty("data")
    public DistanceMetric[] getData(){
        return this.data;
    }

    @JsonProperty("data")
    public void setData(DistanceMetric[] data){
        this.data = data;
    }
}
