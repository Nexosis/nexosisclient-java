package com.nexosis.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "modelId",
        "predictionDomain",
        "dataSourceName",
        "createdDate",
        "algorithm",
        "columns",
        "metrics"
})
public class ModelSummary implements Serializable
{

    @JsonProperty("modelId")
    private UUID modelId;
    @JsonProperty("predictionDomain")
    private PredictionDomain predictionDomain;
    @JsonProperty("dataSourceName")
    private String dataSourceName;
    @JsonProperty("createdDate")
    private DateTime createdDate;
    @JsonProperty("algorithm")
    private Algorithm algorithm;
    @JsonProperty("columns")
    private Columns columns;
    @JsonProperty("metrics")
    private Map<String, Double> metrics;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
    private final static long serialVersionUID = 9059981804434867969L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ModelSummary() {
    }

    @JsonProperty("modelId")
    public UUID getModelId() {
        return modelId;
    }

    @JsonProperty("modelId")
    public void setModelId(UUID modelId) {
        this.modelId = modelId;
    }

    @JsonProperty("predictionDomain")
    public PredictionDomain getPredictionDomain() {
        return predictionDomain;
    }

    @JsonProperty("predictionDomain")
    public void setPredictionDomain(PredictionDomain predictionDomain) {
        this.predictionDomain = predictionDomain;
    }

    @JsonProperty("dataSourceName")
    public String getDataSourceName() {
        return dataSourceName;
    }

    @JsonProperty("dataSourceName")
    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @JsonProperty("createdDate")
    public DateTime getCreatedDate() {
        return createdDate;
    }

    @JsonProperty("createdDate")
    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    @JsonProperty("algorithm")
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    @JsonProperty("algorithm")
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @JsonProperty("columns")
    public Columns getColumns() {
        return columns;
    }

    @JsonProperty("columns")
    public void setColumns(Columns columns) {
        this.columns = columns;
    }

    @JsonProperty("metrics")
    public Map<String,Double> getMetrics() {
        return metrics;
    }

    @JsonProperty("metrics")
    public void setMetrics(Map<String,Double> metrics) {
        this.metrics = metrics;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("modelId", modelId).append("predictionDomain", predictionDomain).append("dataSourceName", dataSourceName).append("createdDate", createdDate).append("algorithm", algorithm).append("columns", columns).append("metrics", metrics).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(modelId).append(metrics).append(additionalProperties).append(columns).append(dataSourceName).append(createdDate).append(predictionDomain).append(algorithm).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ModelSummary) == false) {
            return false;
        }
        ModelSummary rhs = ((ModelSummary) other);
        return new EqualsBuilder().append(modelId, rhs.modelId).append(metrics, rhs.metrics).append(additionalProperties, rhs.additionalProperties).append(columns, rhs.columns).append(dataSourceName, rhs.dataSourceName).append(createdDate, rhs.createdDate).append(predictionDomain, rhs.predictionDomain).append(algorithm, rhs.algorithm).isEquals();
    }

}

