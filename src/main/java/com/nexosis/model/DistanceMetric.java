package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistanceMetric {
    @JsonProperty("anomaly")
    private Float anomaly;
    @JsonProperty("mahalanobis_distance")
    private Float distance;
    private List<Map<String, String>> data = null;

    @JsonProperty("anomaly")
    public Float getAnomaly() {
        return anomaly;
    }

    @JsonProperty("anomaly")
    public void setAnomaly(String anomaly) {
        this.anomaly = Float.parseFloat(anomaly);
    }

    @JsonProperty("mahalanobis_distance")
    public Float getDistance() {
        return distance;
    }

    @JsonProperty("mahalanobis_distance")
    public void setDistance(String distance) {
        this.distance = Float.parseFloat(distance);
    }


    @JsonAnyGetter
    public List<Map<String, String>> getData() {
        return data;
    }

    @JsonAnySetter
    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

}