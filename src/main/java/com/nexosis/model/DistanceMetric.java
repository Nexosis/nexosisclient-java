package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceMetric {
    @JsonProperty("anomaly")
    private double anomaly;
    @JsonProperty("mahalanobis_distance")
    private double distance;
    private Map<String, String> data = new HashMap<String,String>();

    @JsonProperty("anomaly")
    public double getAnomaly() {
        return anomaly;
    }

    @JsonProperty("anomaly")
    public void setAnomaly(String anomaly) {
        this.anomaly = Double.parseDouble(anomaly);
    }

    @JsonProperty("mahalanobis_distance")
    public double getDistance() {
        return distance;
    }

    @JsonProperty("mahalanobis_distance")
    public void setDistance(String distance) {
        this.distance = Double.parseDouble(distance);
    }


    @JsonAnyGetter
    public Map<String, String> getData() {
        return data;
    }

    @JsonAnySetter
    public void setData(String name, String value){
        this.data.put(name, value);
    }

}