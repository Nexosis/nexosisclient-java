package com.nexosis.model;

import java.util.HashMap;
import java.util.Map;

public class ChampionQueryOptions {
    /// The results returned will be from the given prediction interval
    private String predictionInterval;

    public String getPredictionInterval() {
        return predictionInterval;
    }

    public void setPredictionInterval(String predictionInterval) {
        this.predictionInterval = predictionInterval;
    }

    public Map<String, Object> ToParamters() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("predictionInterval", this.getPredictionInterval());
        return queryParams;
    }
}

