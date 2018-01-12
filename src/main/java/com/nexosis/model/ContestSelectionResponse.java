package com.nexosis.model;

import java.util.List;
import java.util.Map;

public class ContestSelectionResponse extends SessionResponse {
    private List<MetricSet> metricSets;

    public List<MetricSet> getMetricSets() {
        return this.metricSets;
    }

    public void setMetricSets(List<MetricSet> metricSets) {
        this.metricSets = metricSets;
    }

    public class MetricSet {
        /// Information about the data source preparations that were performed
        private String[] dataSetProperties;
        /// Selection metrics used when determining which algorithms to run
        private Map<String, Double> metrics;

        public String[] getDataSetProperties() {
            return this.dataSetProperties;
        }

        public void setDataSetProperties(String[] dataSetProperties) {
            this.dataSetProperties = dataSetProperties;
        }

        public Map<String, Double> getMetrics() {
            return metrics;
        }

        public void getMetrics(Map<String, Double> metrics) {
            this.metrics = metrics;
        }
    }
}