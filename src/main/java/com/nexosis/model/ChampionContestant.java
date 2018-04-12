package com.nexosis.model;

import java.util.Map;

public class ChampionContestant {
    /// The Id
    private String id;
    /// Details of the algorithm used
    private Algorithm algorithm;
    /// Information about the datasource preparations that were performed
    private String[] dataSourceProperties;
    /// Scoring metrics generated from the session
    private Map<String, Double> metrics;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Algorithm getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String[] getDataSourceProperties() {
        return dataSourceProperties;
    }

    public void setDataSourceProperties(String[] dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    public Map<String, Double> getMetrics() {
        return metrics;
    }

    public void  setMetrics(Map<String, Double> metrics) {
        this.metrics = metrics;
    }
}
